package com.example.mobiledevelopment.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.R

// Set of Material typography styles to start with
val Typography = Typography(
        body1 = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
        )
        /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


val IBMPlex = FontFamily(
        Font(R.font.ibmplexsans_bold, FontWeight.Bold, FontStyle.Normal),
        Font(R.font.ibmplexsans_bolditalic, FontWeight.Bold, FontStyle.Italic),
        Font(R.font.ibmplexsans_italic, FontWeight.Normal, FontStyle.Italic),
        Font(R.font.ibmplexsans_light, FontWeight.Light, FontStyle.Normal),
        Font(R.font.ibmplexsans_extralight, FontWeight.ExtraLight, FontStyle.Normal),
        Font(R.font.ibmplexsans_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(R.font.ibmplexsans_lightitalic, FontWeight.Light, FontStyle.Italic),
        Font(R.font.ibmplexsans_medium, FontWeight.Medium, FontStyle.Normal),
        Font(R.font.ibmplexsans_mediumitalic, FontWeight.Medium, FontStyle.Italic),
        Font(R.font.ibmplexsans_regular, FontWeight.Normal, FontStyle.Normal),
        Font(R.font.ibmplexsans_semibold, FontWeight.SemiBold, FontStyle.Normal),
        Font(R.font.ibmplexsans_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(R.font.ibmplexsans_thin, FontWeight.Thin, FontStyle.Normal),
        Font(R.font.ibmplexsans_thinitalic, FontWeight.Thin, FontStyle.Italic)
        )