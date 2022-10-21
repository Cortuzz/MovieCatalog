package com.example.mobiledevelopment.src.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.*
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.include.retrofit.MovieElementModel
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.main.domain.ColorGenerator
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineColor

class MainView(activity: MainActivity): Drawable {
    companion object {
        private var instance: MainView? = null

        fun getInstance(activity: MainActivity): MainView {
            if (instance == null)
                instance = MainView(activity)

            return instance as MainView
        }
    }

    private val viewModel: MainViewModel = MainViewModel(this, activity)

    @Composable
    fun PromotedMovie(movieElement: MutableState<MovieElementModel?>) {
        if (movieElement.value == null)
            return

        val gradient = Brush.verticalGradient(
            colors = listOf(Color.Transparent, BackgroundColor),
            startY = with(LocalDensity.current) { 250.dp.toPx() },
            endY = with(LocalDensity.current) { 320.dp.toPx() }
        )

        Box {
            MoviePoster(
                url = movieElement.value!!.poster!!,
                modifier = Modifier.fillMaxWidth().requiredHeight(320.dp),
                contentScale = ContentScale.FillWidth
            )
            Box(modifier = Modifier.matchParentSize().background(gradient))
        }

    }

    @Composable
    fun Favourites(movies: SnapshotStateList<MovieElementModel>) {
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            CategoryText(name = "Избранное")
            FavouritesContent(movies)
        }
    }

    @Composable
    fun FavouritesContent(movies: SnapshotStateList<MovieElementModel>) {
        LazyRow(
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp)
        ) {
            items(movies.size) {
                FavouriteMovieElement(movieElement = movies[it])
            }
        }
    }

    @Composable
    fun FavouriteMovieElement(movieElement: MovieElementModel) {
        SubcomposeAsyncImage(
            model = "${movieElement.poster}",
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp)
                .requiredSize(100.dp, 144.dp)
                ,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.offset(y = 20.dp), color = AccentColor)
                }
                is AsyncImagePainter.State.Error -> ErrorMoviePoster()
                else -> SubcomposeAsyncImageContent()
            }
        }
    }

    @Composable
    fun Gallery(movies: SnapshotStateList<MovieElementModel>) {
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            CategoryText(name = "Галерея")

            Spacer(modifier = Modifier.height(8.dp))

            for (movie in movies) {
               MovieElement(movieElement = movie)
                Spacer(Modifier.height(16.dp))
            }

            Spacer(Modifier.height(50.dp))
        }
    }

    @Composable
    fun CategoryText(name: String) {
        Text(text = name,
            color = AccentColor,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun TitleText(name: String) {
        Text(text = name,
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun DescriptionText(name: String) {
        Text(text = name,
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun MovieElement(movieElement: MovieElementModel) {
        var parsedGenres = ""
        for (genre in movieElement.genres!!) {
            parsedGenres += "${genre.name}, " // todo: переделать
        }
        parsedGenres = parsedGenres.dropLast(2)

        val rating = ColorGenerator.getRating(movieElement.reviews!!)
        val height = remember { mutableStateOf(30) }
        val heightDp = with(LocalDensity.current) { height.value.toDp() }

        Row {
            MoviePoster(url = movieElement.poster!!, modifier = Modifier
                .onGloballyPositioned { height.value = it.size.height }
                .requiredSize(100.dp, 144.dp))

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .offset(y = (-6).dp)
                    .defaultMinSize(minHeight = heightDp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    TitleText(name = movieElement.name!!)
                    Spacer(modifier = Modifier.height(3.dp))
                    DescriptionText(name = "${movieElement.year} • ${movieElement.country}")
                    Spacer(modifier = Modifier.height(3.dp))
                    DescriptionText(name = parsedGenres)
                }

                Spacer(modifier = Modifier.height(12.dp))
                RatingShape(rating = rating)
            }
        }
    }

    @Composable
    fun ErrorMoviePoster() {
        val matrix = ColorMatrix()
        matrix.setToSaturation(0F)

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_no_text),
                modifier = Modifier.requiredSize(100.dp),
                contentDescription = "No image",
                colorFilter = ColorFilter.colorMatrix(matrix)
            )

            Text(text = "No image",
                color = Color.Gray,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                lineHeight = 14.sp,
                letterSpacing = 0.5.sp,
            )
        }

    }

    @Composable
    fun MoviePoster(url: String,
                    modifier: Modifier,
                    contentScale: ContentScale = ContentScale.Fit
    ) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = modifier,
            contentScale = contentScale,
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.offset(y = 20.dp), color = AccentColor)
                }
                is AsyncImagePainter.State.Error -> ErrorMoviePoster()
                else -> SubcomposeAsyncImageContent()
            }
        }
    }

    @Composable
    fun RatingShape(rating: Float) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.BottomStart)
        ) {
            Box(
                modifier = Modifier
                    .size(height = 28.dp, width = 56.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ColorGenerator.getColor(rating))
            ) {
                Text(
                    modifier = Modifier.fillMaxSize(),
                    text = if (rating.isNaN()) "—" else rating.toString(),
                    color = Color.White,
                    fontFamily = IBMPlex,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    letterSpacing = 0.5.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun Navigation() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            NavigationButton(
                name = "Главное",
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.main_page)
            )
            NavigationButton(
                name = "Профиль",
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.profile_page)
            )
        }

    }

    @Composable
    fun NavigationButton(
        name: String,
        onClick: () -> Unit,
        painter: Painter
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.width(150.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = BackgroundColor)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painter,
                    contentDescription = null,
                )

                Text(
                    text = name,
                    color = OutlineColor,
                )
            }
        }
    }

    @Composable
    override fun Draw() {
        LazyColumn {
            item { PromotedMovie(viewModel.getPromotedMovie()) }
            item { Favourites(viewModel.getFavouriteMovieList()) }
            item { Gallery(viewModel.getMovieList()) }
        }

        //Navigation()
    }
}
