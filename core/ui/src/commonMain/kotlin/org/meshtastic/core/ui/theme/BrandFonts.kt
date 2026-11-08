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
package org.meshtastic.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.meshtastic.core.resources.Res
import org.meshtastic.core.resources.barlow_medium
import org.meshtastic.core.resources.barlow_regular
import org.meshtastic.core.resources.barlow_sc_bold
import org.meshtastic.core.resources.barlow_sc_semibold
import org.meshtastic.core.resources.barlow_semibold

/**
 * Smartlab LoRa typefaces: Barlow for running text and Barlow Semi Condensed for headings (both SIL Open Font License
 * 1.1, from Google Fonts). Bundled with the app, so no downloadable-font service is needed.
 */
@Immutable
data class BrandFonts(val body: FontFamily, val display: FontFamily)

@Composable
fun rememberBrandFonts(): BrandFonts {
    val regular = Font(Res.font.barlow_regular, FontWeight.Normal)
    val medium = Font(Res.font.barlow_medium, FontWeight.Medium)
    val semiBold = Font(Res.font.barlow_semibold, FontWeight.SemiBold)
    val displaySemiBold = Font(Res.font.barlow_sc_semibold, FontWeight.SemiBold)
    val displayBold = Font(Res.font.barlow_sc_bold, FontWeight.Bold)
    return remember(regular, medium, semiBold, displaySemiBold, displayBold) {
        BrandFonts(
            body = FontFamily(regular, medium, semiBold),
            display = FontFamily(displaySemiBold, displayBold),
        )
    }
}

/** Applies the brand typefaces to the Material type scale: display, headline and large titles use the condensed face. */
fun Typography.withBrandFonts(fonts: BrandFonts): Typography = copy(
    displayLarge = displayLarge.copy(fontFamily = fonts.display),
    displayMedium = displayMedium.copy(fontFamily = fonts.display),
    displaySmall = displaySmall.copy(fontFamily = fonts.display),
    headlineLarge = headlineLarge.copy(fontFamily = fonts.display),
    headlineMedium = headlineMedium.copy(fontFamily = fonts.display),
    headlineSmall = headlineSmall.copy(fontFamily = fonts.display),
    titleLarge = titleLarge.copy(fontFamily = fonts.display),
    titleMedium = titleMedium.copy(fontFamily = fonts.body),
    titleSmall = titleSmall.copy(fontFamily = fonts.body),
    bodyLarge = bodyLarge.copy(fontFamily = fonts.body),
    bodyMedium = bodyMedium.copy(fontFamily = fonts.body),
    bodySmall = bodySmall.copy(fontFamily = fonts.body),
    labelLarge = labelLarge.copy(fontFamily = fonts.body),
    labelMedium = labelMedium.copy(fontFamily = fonts.body),
    labelSmall = labelSmall.copy(fontFamily = fonts.body),
)
