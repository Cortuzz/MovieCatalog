package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.src.domain.main.noImageText
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.IBMPlex

@Composable
fun MoviePoster(url: String,
                modifier: Modifier,
                contentScale: ContentScale = ContentScale.Fit,
                showLoading: Boolean = true
) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale,
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                if (showLoading)
                    CircularProgressIndicator(modifier = Modifier.offset(y = 20.dp), color = AccentColor)
            }
            is AsyncImagePainter.State.Error -> if (!showLoading) {
                ErrorMoviePoster(
                    Modifier.requiredSize(200.dp),
                    Arrangement.Center
                )
            } else {
                ErrorMoviePoster()
            }
            else -> SubcomposeAsyncImageContent()
        }
    }
}

@Composable
fun ErrorMoviePoster(
    modifier: Modifier = Modifier.requiredSize(100.dp),
    arrangement: Arrangement.Vertical = Arrangement.Top
) {
    val matrix = ColorMatrix()
    matrix.setToSaturation(0F)

    Column(
        verticalArrangement = arrangement,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo_no_text),
            modifier = modifier,
            contentDescription = noImageText,
            colorFilter = ColorFilter.colorMatrix(matrix)
        )

        Text(text = noImageText,
            color = Color.Gray,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            lineHeight = 14.sp,
            letterSpacing = 0.5.sp,
        )
    }

}