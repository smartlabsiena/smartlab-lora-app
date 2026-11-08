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
package org.meshtastic.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Calendar

private val SplashNavy = Color(0xFF142B3A)
private val SplashIvory = Color(0xFFF5E9D8)
private val SplashTeal = Color(0xFF63D4C6)

/**
 * Smartlab LoRa start screen: Siena skyline, app name, author line, and a footer with the current year (read from the
 * phone) and the app version. Shown once per launch, above the app, then faded out by the caller.
 */
@Composable
fun SmartlabSplash(modifier: Modifier = Modifier) {
    val year = remember { Calendar.getInstance().get(Calendar.YEAR) }
    Box(
        modifier = modifier.fillMaxSize().background(SplashNavy).pointerInput(Unit) {},
    ) {
        Column(
            modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Image(
                painter = painterResource(R.drawable.splash_siena),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxWidth(0.78f),
            )
            Spacer(Modifier.height(28.dp))
            Text(
                text = "Smartlab LoRa",
                color = SplashIvory,
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "by Smart Lab Siena",
                color = SplashTeal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }
        Text(
            text = "© $year · v${BuildConfig.VERSION_NAME}",
            color = SplashIvory.copy(alpha = 0.6f),
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(Alignment.BottomCenter).safeDrawingPadding().padding(bottom = 16.dp),
        )
    }
}
