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
package org.meshtastic.feature.map.maplibre.layers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.maplibre.compose.expressions.dsl.asString
import org.maplibre.compose.expressions.dsl.const
import org.maplibre.compose.expressions.dsl.feature
import org.maplibre.compose.expressions.dsl.textOffset
import org.maplibre.compose.interaction.ClickResult
import org.maplibre.compose.layers.CircleLayer
import org.maplibre.compose.layers.SymbolLayer
import org.maplibre.compose.util.MaplibreComposable
import org.maplibre.spatialk.geojson.Feature
import org.maplibre.spatialk.geojson.FeatureCollection
import org.maplibre.spatialk.geojson.Point
import org.maplibre.spatialk.geojson.Position
import org.meshtastic.feature.map.areas.EmergencyArea
import org.meshtastic.feature.map.areas.EmergencyAreaFocus
import org.meshtastic.feature.map.maplibre.geojson.rememberFeatureSource

private const val KEY_NAME = "name"
private const val LABEL_OFFSET_EM = 1.6f
private val AreaColor = Color(0xFFD83A2E)

/**
 * The one emergency area picked from the Kit, as a single labelled point. Nothing is drawn when none is picked, so the
 * areas never clutter the mesh nodes. The coordinates come from the app itself: no network needed. Tapping the point
 * removes it.
 */
@Composable
@MaplibreComposable
internal fun EmergencyAreaLayers(area: EmergencyArea) {
    val source =
        rememberFeatureSource(area) {
            val lat = area.lat
            val lon = area.lon
            FeatureCollection(
                if (lat == null || lon == null) {
                    emptyList()
                } else {
                    listOf(
                        Feature<Point, JsonObject?>(
                            geometry = Point(Position(longitude = lon, latitude = lat)),
                            properties = buildJsonObject { put(KEY_NAME, area.name) },
                        ),
                    )
                },
            )
        }

    CircleLayer(
        id = "emergency-area-halo",
        source = source,
        color = const(AreaColor.copy(alpha = 0.25f)),
        radius = const(22.dp),
    )
    CircleLayer(
        id = "emergency-area-point",
        source = source,
        color = const(AreaColor),
        radius = const(9.dp),
        strokeColor = const(Color.White),
        strokeWidth = const(3.dp),
        onClick = {
            EmergencyAreaFocus.clear()
            ClickResult.Consume
        },
    )
    SymbolLayer(
        id = "emergency-area-label",
        source = source,
        textField = feature[KEY_NAME].asString(),
        textFont = const(listOf("Noto Sans Regular")),
        textColor = const(Color.White),
        textHaloColor = const(Color.Black),
        textHaloWidth = const(1.5.dp),
        textOffset = textOffset(0f.em, LABEL_OFFSET_EM.em),
    )
}
