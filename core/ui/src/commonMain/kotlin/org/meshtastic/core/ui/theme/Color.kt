/*
 * Copyright (c) 2026 Meshtastic LLC
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

import androidx.compose.ui.graphics.Color

// ─── Smartlab LoRa palette (modified 2026-11-06 from the Meshtastic Design Standards v1.3 scheme) ───
// Brand: navy #142B3A | terracotta #D87855 (light #E88963) | teal #63D4C6 | ivory #F5E9D8
// Dark = navy surfaces, teal primary, terracotta secondary, ivory text.
// Light = ivory surfaces, deep-teal primary, dark-terracotta secondary, navy tertiary.
// Error colours are kept from the original scheme (accessibility).

// ─── Light Scheme ───
val primaryLight = Color(0xFF1B7F75) // Deep teal (contrast ≥ 4.5 on white)
val onPrimaryLight = Color(0xFFFFFFFF)
val primaryContainerLight = Color(0xFFBFF3EC) // Light teal
val onPrimaryContainerLight = Color(0xFF00201C)
val secondaryLight = Color(0xFFB25A38) // Dark terracotta
val onSecondaryLight = Color(0xFFFFFFFF)
val secondaryContainerLight = Color(0xFFFFDBCB)
val onSecondaryContainerLight = Color(0xFF3A1A0C)
val tertiaryLight = Color(0xFF142B3A) // Brand navy
val onTertiaryLight = Color(0xFFFFFFFF)
val tertiaryContainerLight = Color(0xFFD5E4EC)
val onTertiaryContainerLight = Color(0xFF0B1A24)
val errorLight = Color(0xFFBA1A1A) // Error 600 (WCAG-safe on white)
val onErrorLight = Color(0xFFFFFFFF)
val errorContainerLight = Color(0xFFFDEAEA) // Error 100
val onErrorContainerLight = Color(0xFF410002) // Error 900
val backgroundLight = Color(0xFFFBF6EE) // Warm ivory (lighter than brand ivory)
val onBackgroundLight = Color(0xFF1B2A33)
val surfaceLight = Color(0xFFFBF6EE)
val onSurfaceLight = Color(0xFF1B2A33)
val surfaceVariantLight = Color(0xFFE6DDCD)
val onSurfaceVariantLight = Color(0xFF4E5A61)
val outlineLight = Color(0xFF7B858A)
val outlineVariantLight = Color(0xFFD3C9B8)
val scrimLight = Color(0xFF000000)
val inverseSurfaceLight = Color(0xFF142B3A)
val inverseOnSurfaceLight = Color(0xFFF5E9D8)
val inversePrimaryLight = Color(0xFF63D4C6)
val surfaceDimLight = Color(0xFFE3D9C8)
val surfaceBrightLight = Color(0xFFFBF6EE)
val surfaceContainerLowestLight = Color(0xFFFFFFFF)
val surfaceContainerLowLight = Color(0xFFFBF6EE)
val surfaceContainerLight = Color(0xFFF5E9D8) // Brand ivory
val surfaceContainerHighLight = Color(0xFFEDE2D0)
val surfaceContainerHighestLight = Color(0xFFE6DAC6)

// ─── Dark Scheme ───
val primaryDark = Color(0xFF63D4C6) // Brand teal
val onPrimaryDark = Color(0xFF00201C)
val primaryContainerDark = Color(0xFF0F5A52)
val onPrimaryContainerDark = Color(0xFFA7F2E9)
val secondaryDark = Color(0xFFE88963) // Light terracotta
val onSecondaryDark = Color(0xFF3A1A0C)
val secondaryContainerDark = Color(0xFF7A3A22)
val onSecondaryContainerDark = Color(0xFFFFDBCB)
val tertiaryDark = Color(0xFFF5E9D8) // Brand ivory
val onTertiaryDark = Color(0xFF3A2F1E)
val tertiaryContainerDark = Color(0xFF4D4332)
val onTertiaryContainerDark = Color(0xFFF5E9D8)
val errorDark = Color(0xFFFFB4AB) // Error 300
val onErrorDark = Color(0xFF690005) // Error 800
val errorContainerDark = Color(0xFF93000A) // Error 700
val onErrorContainerDark = Color(0xFFFDEAEA) // Error 100
val backgroundDark = Color(0xFF0F212C) // Darker than brand navy
val onBackgroundDark = Color(0xFFF1E8D8)
val surfaceDark = Color(0xFF0F212C)
val onSurfaceDark = Color(0xFFF1E8D8)
val surfaceVariantDark = Color(0xFF234354)
val onSurfaceVariantDark = Color(0xFFC5D0D6)
val outlineDark = Color(0xFF7F949F)
val outlineVariantDark = Color(0xFF36505F)
val scrimDark = Color(0xFF000000)
val inverseSurfaceDark = Color(0xFFF5E9D8)
val inverseOnSurfaceDark = Color(0xFF142B3A)
val inversePrimaryDark = Color(0xFF1B7F75)
val surfaceDimDark = Color(0xFF0A1822)
val surfaceBrightDark = Color(0xFF2A4B5E)
val surfaceContainerLowestDark = Color(0xFF0A1822)
val surfaceContainerLowDark = Color(0xFF0F212C)
val surfaceContainerDark = Color(0xFF142B3A) // Brand navy
val surfaceContainerHighDark = Color(0xFF1B3646)
val surfaceContainerHighestDark = Color(0xFF234354)
