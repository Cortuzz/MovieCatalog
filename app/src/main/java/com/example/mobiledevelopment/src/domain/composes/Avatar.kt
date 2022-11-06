package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mobiledevelopment.R

@Composable
fun Avatar(url: String, modifier: Modifier) {
    SubcomposeAsyncImage(
        model = url,
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.FillBounds
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Error -> {
                Image(
                    painter = painterResource(id = R.drawable.avatar),
                    contentDescription = "No avatar"
                )
            }
            else -> SubcomposeAsyncImageContent()
        }
    }
}