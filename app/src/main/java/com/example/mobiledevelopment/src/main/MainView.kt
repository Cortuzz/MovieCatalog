package com.example.mobiledevelopment.src.main

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.src.domain.utils.services.RatingProviderService
import com.example.mobiledevelopment.src.domain.composes.*
import com.example.mobiledevelopment.src.domain.main.favoriteBlockText
import com.example.mobiledevelopment.src.domain.main.galleryBlockText
import com.example.mobiledevelopment.src.domain.models.MovieElementModel
import com.example.mobiledevelopment.src.domain.utils.noRippleClickable
import com.example.mobiledevelopment.ui.theme.*

private var viewModel: MainViewModel = MainViewModel()
private lateinit var navigateToMovie: () -> Unit
private lateinit var navigateToLogin: () -> Unit
private lateinit var navigateToProfile: () -> Unit

@Composable
fun PromotedMovie(movieElement: MutableState<MovieElementModel?>) {
    val gradient = Brush.verticalGradient(
        colors = listOf(Color.Transparent, BackgroundColor),
        startY = with(LocalDensity.current) { 250.dp.toPx() },
        endY = with(LocalDensity.current) { 320.dp.toPx() }
    )

    Box(
        modifier = Modifier.defaultMinSize(minHeight = 320.dp)
    ) {
        if (movieElement.value != null) {
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

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = 244.dp),
                horizontalArrangement = Arrangement.Center) {
                PrimaryButton(
                    name = "Смотреть",
                    action = {
                        if (movieElement.value != null) {
                            viewModel.openMovie(movieElement.value!!)
                            navigateToMovie()
                        }
                    },
                    modifier = Modifier.defaultMinSize(minHeight = 44.dp, minWidth = 160.dp)
                )
            }
        }
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
    val state = rememberForeverLazyListState(key = "favourites")
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
            .requiredHeight(180.dp)
            .padding(top = 8.dp)
    ) {
        items(movies.size) {
            val height by animateDpAsState(
                targetValue = if (startIndex == it) 172.dp else 144.dp,
                animationSpec = tween(300),
            )
            val width by animateDpAsState(
                targetValue = if (startIndex == it) 120.dp else 100.dp,
                animationSpec = tween(300),
            )
            val padding by animateDpAsState(
                targetValue = if (startIndex == it) 0.dp else 14.dp,
                animationSpec = tween(300),
            )

            val modifier = Modifier
                .padding(end = 16.dp, top = padding)
                .animateContentSize()
                .requiredSize(width, height)
                .clip(shape = RoundedCornerShape(16.dp))
                .noRippleClickable {
                    viewModel.openMovie(movies[it])
                    navigateToMovie()
                }

            FavouriteMovieElement(movieElement = movies[it], modifier)
        }
    }
}

@Composable
fun FavouriteMovieElement(movieElement: MovieElementModel, modifier: Modifier) {
    SubcomposeAsyncImage(
        contentScale = ContentScale.FillBounds,
        model = "${movieElement.poster}",
        contentDescription = null,
        modifier = modifier
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
                        navigateToLogin()
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
                .noRippleClickable { onClick() },
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

    val rating = RatingProviderService.getRating(movieElement.reviews ?: listOf())
    val height = remember { mutableStateOf(30) }
    val positioned = remember { mutableStateOf(false) }
    val heightDp = with(LocalDensity.current) { height.value.toDp() }

    Row(
        modifier = Modifier.noRippleClickable {
            viewModel.openMovie(movieElement)
            navigateToMovie()
        }
    ) {
        MoviePoster(url = movieElement.poster!!, modifier = Modifier
            .onGloballyPositioned {
                height.value = it.size.height
                positioned.value = true
            }
            .requiredSize(100.dp, 144.dp)
            .clip(shape = RoundedCornerShape(16.dp))
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

            if (positioned.value)
                RatingShape(
                    rating = rating,
                    modifier = Modifier.size(height = 28.dp, width = 56.dp)
                )
        }
    }
}

@Composable
fun Navigation() {
    Row(
      modifier = Modifier.fillMaxSize(),
      verticalAlignment = Alignment.Bottom,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(NavigationColor),

        ) {
            NavigationButton(
                name = "Главное",
                onClick = {  },
                painter = painterResource(id = R.drawable.main_page),
                fraction = 0.5f,
            )
            NavigationButton(
                name = "Профиль",
                onClick = { navigateToProfile() },
                painter = painterResource(id = R.drawable.profile_page),
                fraction = 1f,
            )
        }
    }
}

@Composable
fun MoviesLoadingIndicator() {
    val isEndObtained = viewModel.getTotalPages().value < viewModel.getCurrentPage().value

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.Center
    ) {
        LoadingIndicator(modifier = Modifier.requiredSize(150.dp)) { !isEndObtained }
        if (isEndObtained) {
            Spacer(Modifier.height(25.dp))
            Text(
                text = "Больше фильмов нет\nНо скоро появятся",
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}

@Composable
fun MainScreen(navToLogin: () -> Unit, navToMovie: () -> Unit, navToProfile: () -> Unit) {
    navigateToLogin = navToLogin
    navigateToMovie = navToMovie
    navigateToProfile = navToProfile
    val state = rememberForeverLazyListState(key = "main_screen")

    PullRefresh(onRefresh = { viewModel.refresh { navToLogin() } }) {
        LazyColumn(
            state = state
        ) {
            item { PromotedMovie(viewModel.getPromotedMovie()) }
            item { Favourites(viewModel.getFavouriteMovieList()) }
            item { Gallery(viewModel.getMovieList()) }
            item { MoviesLoadingIndicator() }
            item { Spacer(Modifier.height(80.dp)) }
            item { viewModel.fetchNextPage() }
        }

        Navigation()
    }
}