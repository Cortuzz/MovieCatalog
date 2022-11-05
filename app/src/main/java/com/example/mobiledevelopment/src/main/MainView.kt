package com.example.mobiledevelopment.src.main

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.include.retrofit.MovieElementModel
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.main.domain.ColorGenerator
import com.example.mobiledevelopment.src.main.domain.favoriteBlockText
import com.example.mobiledevelopment.src.main.domain.galleryBlockText
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.BackgroundColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineColor
import com.example.mobiledevelopment.ui.theme.composes.ErrorMoviePoster
import com.example.mobiledevelopment.ui.theme.composes.MoviePoster
import com.example.mobiledevelopment.ui.theme.composes.RatingShape
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

class MainView(private val navController: NavHostController): Drawable {
    companion object {
        private var instance: MainView? = null

        fun getInstance(nav: NavHostController): MainView {
            if (instance == null)
                instance = MainView(nav)

            return instance as MainView
        }
    }

    private var viewModel: MainViewModel = MainViewModel()

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
                modifier = Modifier
                    .fillMaxWidth()
                    .requiredHeight(320.dp),
                contentScale = ContentScale.FillWidth,
                showLoading = false
            )
            Box(modifier = Modifier
                .matchParentSize()
                .background(gradient))
        }

    }

    @Composable
    fun Favourites(movies: SnapshotStateList<MovieElementModel>) {
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            CategoryText(name = favoriteBlockText)
            FavouritesContent(movies)
        }
    }

    @Composable
    fun FavouritesContent(movies: SnapshotStateList<MovieElementModel>) {
        val state = rememberLazyListState()
        val startIndex by remember(remember { derivedStateOf { state.firstVisibleItemIndex } }) {
            derivedStateOf {
                state.layoutInfo.visibleItemsInfo.run {
                    val firstVisibleIndex = state.firstVisibleItemIndex
                    if (isEmpty()) -1 else firstVisibleIndex + (last().index - firstVisibleIndex) / 2
                }
            }
        }

        LazyRow(
            state = state,
            modifier = Modifier
                .fillMaxHeight()
                .padding(top = 8.dp)
        ) {
            items(movies.size) {
                if (startIndex == it) 
                    FavouriteMovieElement(movieElement = movies[it], 120.dp, 172.dp, 0.dp)
                else
                    FavouriteMovieElement(movieElement = movies[it])
            }
        }
    }

    @Composable
    fun FavouriteMovieElement(
        movieElement: MovieElementModel,
        width: Dp = 100.dp,
        height: Dp = 144.dp,
        padding: Dp = 14.dp) {


        SubcomposeAsyncImage(
            contentScale = ContentScale.FillBounds,
            model = "${movieElement.poster}",
            contentDescription = null,
            modifier = Modifier
                .padding(end = 16.dp, top = padding)
                .animateContentSize()
                .requiredSize(width, height)
                .clip(shape = RoundedCornerShape(16.dp))
                .clickable {
                    viewModel.openMovie(movieElement);
                    navController.navigate("movie_screen")
                }
        ) {
            when (painter.state) {
                is AsyncImagePainter.State.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.offset(y = 20.dp), color = AccentColor)
                }
                is AsyncImagePainter.State.Error -> ErrorMoviePoster()
                else -> {
                    SubcomposeAsyncImageContent()
                    RemoveButton {
                        viewModel.removeFromFavourites(movieElement) {
                            navController.navigate("login_screen") {
                                popUpTo(0)
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun RemoveButton(onClick: () -> Unit) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 6.dp, end = 6.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier
                    .requiredSize(16.dp)
                    .clickable { onClick() },
                painter = painterResource(id = R.drawable.remove_button),
                contentDescription = "Remove",
            )
        }
    }

    @Composable
    fun Gallery(movies: SnapshotStateList<MovieElementModel>) {
        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            CategoryText(name = galleryBlockText)

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

        val rating = ColorGenerator.getRating(movieElement.reviews ?: listOf())
        val height = remember { mutableStateOf(30) }
        val heightDp = with(LocalDensity.current) { height.value.toDp() }

        Row {
            MoviePoster(url = movieElement.poster!!, modifier = Modifier
                .onGloballyPositioned { height.value = it.size.height }
                .requiredSize(100.dp, 144.dp)
                .clip(shape = RoundedCornerShape(16.dp))
                .clickable { viewModel.openMovie(movieElement); navController.navigate("movie_screen") }
            )

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
                RatingShape(rating = rating, modifier = Modifier.size(height = 28.dp, width = 56.dp))
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
        val scrollState = rememberLazyListState()
        val endOfListReached by remember {
            derivedStateOf {
                scrollState.layoutInfo.visibleItemsInfo.lastIndex
            }
        }

    PullRefresh(onRefresh = { viewModel.refresh { navToLogin() } }) {
        LazyColumn(
            state = state
        ) {
            LazyColumn {
                item { PromotedMovie(viewModel.getPromotedMovie()) }
                item { Favourites(viewModel.getFavouriteMovieList()) }
                item { Gallery(viewModel.getMovieList()) }
                item { viewModel.fetchNextPage() }
            }
        }




        //Navigation()
    }
}
