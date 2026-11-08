/*
 * Copyright (c) 2026 Smart Lab Siena
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.meshtastic.core.ui.component

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.unit.IntSize
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin
import kotlin.random.Random

/** Classic woodland camouflage layers, painted back to front: sand, green, brown, near-black. */
private class CamoLayer(val color: Color, val count: Int, val size: Float, val stretch: Float)

private val CamoLayers =
    listOf(
        CamoLayer(Color(0xFFB5A66E), 10, 0.30f, 1.5f), // sand
        CamoLayer(Color(0xFF3F5E1E), 16, 0.28f, 1.9f), // green
        CamoLayer(Color(0xFF6B3E14), 14, 0.20f, 2.5f), // brown
        CamoLayer(Color(0xFF2A1D10), 10, 0.11f, 1.7f), // near-black
    )

private const val POINTS = 8

/**
 * Light "woodland camouflage" texture drawn over the content: elongated, rounded blobs in sand, green, brown and
 * dark brown at very low opacity. The pattern is identical on every launch (fixed seed). It does not intercept
 * touches, so it works over every screen and menu.
 */
fun Modifier.camoOverlay(enabled: Boolean = true, alpha: Float = 0.2f): Modifier = if (!enabled) this else drawWithCache {
    val w = size.width
    val h = size.height
    val rnd = Random(868)
    val base = min(w, h)
    val mid = { a: Offset, b: Offset -> Offset((a.x + b.x) / 2, (a.y + b.y) / 2) }
    val blobs =
        CamoLayers.flatMap { layer ->
            List(layer.count) {
                val cx = rnd.nextFloat() * w
                val cy = rnd.nextFloat() * h
                val r = base * layer.size * (0.6f + rnd.nextFloat() * 0.8f)
                val rot = rnd.nextFloat() * PI.toFloat()
                val stretch = layer.stretch * (0.7f + rnd.nextFloat() * 0.6f)
                val pts =
                    List(POINTS) { i ->
                        val a = 2.0 * PI * i / POINTS
                        val rr = r * (0.55f + rnd.nextFloat() * 0.9f)
                        val x = cos(a).toFloat() * rr * stretch
                        val y = sin(a).toFloat() * rr
                        Offset(
                            cx + x * cos(rot) - y * sin(rot),
                            cy + x * sin(rot) + y * cos(rot),
                        )
                    }
                val path = Path()
                val start = mid(pts.last(), pts.first())
                path.moveTo(start.x, start.y)
                for (i in pts.indices) {
                    val next = mid(pts[i], pts[(i + 1) % POINTS])
                    path.quadraticTo(pts[i].x, pts[i].y, next.x, next.y)
                }
                path.close()
                path to layer.color
            }
        }
    // The blobs are drawn fully opaque ONCE into a half-resolution bitmap; every frame only blends that bitmap with the
    // wanted alpha. Crisp edges, no fog, and no per-frame path drawing or full-screen layer (that made screens slow).
    val bw = (w * CAMO_SCALE).toInt().coerceAtLeast(1)
    val bh = (h * CAMO_SCALE).toInt().coerceAtLeast(1)
    val bitmap = ImageBitmap(bw, bh)
    CanvasDrawScope().draw(this, layoutDirection, Canvas(bitmap), Size(bw.toFloat(), bh.toFloat())) {
        scale(CAMO_SCALE, CAMO_SCALE, pivot = Offset.Zero) {
            blobs.forEach { (path, color) -> drawPath(path, color = color) }
        }
    }
    onDrawWithContent {
        drawContent()
        drawImage(
            image = bitmap,
            dstSize = IntSize(w.toInt(), h.toInt()),
            alpha = alpha,
            filterQuality = FilterQuality.Low,
        )
    }
}

private const val CAMO_SCALE = 0.5f
