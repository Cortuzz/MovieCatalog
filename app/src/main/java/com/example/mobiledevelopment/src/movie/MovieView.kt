package com.example.mobiledevelopment.src.movie

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.include.retrofit.MovieDetailsModel
import com.example.mobiledevelopment.include.retrofit.ReviewModel
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.utils.Utils
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.DialogColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineReviewColor
import com.example.mobiledevelopment.ui.theme.composes.*
import com.google.accompanist.flowlayout.FlowRow

class MovieView(private val navController: NavHostController): Drawable {
    companion object {
        private var instance: MovieView? = null

        fun getInstance(nav: NavHostController): MovieView {
            if (instance == null)
                instance = MovieView(nav)

            val view = (instance as MovieView)
            view.viewModel.getMovie()
            view.viewModel.clearReview()

            return instance as MovieView
        }
    }

    private val viewModel = MovieViewModel()
    private var reviewDialogOpened = mutableStateOf(false)

    @Composable
    fun Header(movieModelState: MutableState<MovieDetailsModel?>) {
        val movieModel = movieModelState.value ?: return

        MoviePoster(
            url = movieModel.poster ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(250.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth,
            showLoading = false
        )
    }

    @Composable
    fun Description(movieModelState: MutableState<MovieDetailsModel?>) {
        val description = movieModelState.value?.description ?: return
        DescriptionText(text = description)
    }

    @Composable
    fun About(movieModelState: MutableState<MovieDetailsModel?>) {
        val movieModel = movieModelState.value ?: return

        Column {
            CategoryText(text = "О фильме")
            Spacer(modifier = Modifier.height(8.dp))

            AboutPair(title = "Год", value = movieModel.year.toString())
            AboutPair(title = "Страна", value = movieModel.country)
            AboutPair(title = "Время", value = "${movieModel.time} мин.")
            AboutPair(title = "Слоган", value = movieModel.tagline)
            AboutPair(title = "Режиссер", value = movieModel.director)
            AboutPair(title = "Бюджет", value = Utils.parseMoney(movieModel.budget))
            AboutPair(title = "Сборы в мире", value = Utils.parseMoney(movieModel.fees))
            AboutPair(title = "Возраст", value = "${movieModel.ageLimit}+")
        }
    }

    @Composable
    fun AboutPair(title: String, value: String?) {
        Row {
            AboutText(
                text = title,
                isTitle = true,
                color = Color(0xFFB3B3B3)
            )

            Spacer(modifier = Modifier.width(8.dp))

            AboutText(
                text = if (value.isNullOrBlank()) "Нет информации" else value,
                isTitle = false,
                color = Color.White
            )
        }
    }

    @Composable
    fun GenreBlock(text: String) {
        Box(
            modifier = Modifier
                .padding(top = 8.dp)
                .requiredHeight(height = 27.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(color = AccentColor)
                .wrapContentWidth(),
        ) {
            AboutText(text = text, isTitle = false, align = TextAlign.Center, Color.White, modifier = Modifier.offset(y = 3.dp))
        }
    }

    @Composable
    fun Genres(movieModelState: MutableState<MovieDetailsModel?>) {
        val movieModel = movieModelState.value ?: return

        Column {
            CategoryText(text = "Жанры")
            FlowRow {
                for (genre in movieModel.genres) {
                    GenreBlock(text = genre.name ?: "")
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }

    @Composable
    fun Avatar(url: String) {
        SubcomposeAsyncImage(
            model = url,
            contentDescription = null,
            modifier = Modifier
                .requiredSize(40.dp)
                .clip(CircleShape),
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
    
    @Composable
    fun ReviewHeader(review: ReviewModel) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Avatar(url = review.author?.avatar ?: "")
                Spacer(modifier = Modifier.width(8.dp))
                CategoryText(
                    text = review.author?.nickName ?: "Анонимный пользователь",
                    modifier = Modifier.offset(y = (-1).dp)
                )
            }
            
            RatingShape(
                rating = review.rating,
                modifier = Modifier.size(height = 28.dp, width = 42.dp),
            )
        }
    }

    @Composable
    fun ReviewBody(review: ReviewModel) {
        DescriptionText(text = review.reviewText ?: "")
    }

    @Composable
    fun ReviewFooter(review: ReviewModel) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AboutText(
                text = Utils.parseTimestamp(review.createDateTime),
                isTitle = false,
                color = Color(0xFFB7B7B7)
            )
        }
    }
    
    @Composable
    fun Review(review: ReviewModel) {
        Box(
            modifier = Modifier
                .border(BorderStroke(1.dp, OutlineReviewColor), RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                ReviewHeader(review = review)
                Spacer(modifier = Modifier.height(8.dp))

                ReviewBody(review = review)
                Spacer(modifier = Modifier.height(4.dp))

                ReviewFooter(review = review)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    fun AddReviewButton() {
        Image(
            painter = painterResource(id = R.drawable.plus),
            contentDescription = "Добавить",
            modifier = Modifier.clickable { reviewDialogOpened.value = true }
        )
    }

    @Composable
    fun Reviews(movieModelState: MutableState<MovieDetailsModel?>) {
        val movieModel = movieModelState.value ?: return

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryText(text = "Отзывы")
                AddReviewButton()
            }

            Spacer(modifier = Modifier.height(12.dp))

            for (review in movieModel.reviews) {
                Review(review = review)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    @Composable
    fun MainContent(movieModel: MutableState<MovieDetailsModel?>) {
        if (movieModel.value == null)
            return

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Description(movieModel)
            Spacer(modifier = Modifier.height(16.dp))

            About(movieModel)
            Spacer(modifier = Modifier.height(16.dp))

            Genres(movieModel)
            Spacer(modifier = Modifier.height(16.dp))

            Reviews(movieModel)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    override fun Draw() {
        val movieModel = viewModel.getMovieModel()

        if (reviewDialogOpened.value) {
            ReviewDialog { reviewDialogOpened.value = false }
        }

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            Header(movieModel)
            Spacer(modifier = Modifier.height(15.3.dp))
            MainContent(movieModel)
        }

    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun ReviewDialog(onDismissRequest: () -> Unit) {
        AlertDialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 0.dp),
            backgroundColor = DialogColor,
            shape = RoundedCornerShape(16.dp),
            onDismissRequest = onDismissRequest,
            text = {
                ReviewDialogContent()
            },
            buttons = {
                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    PrimaryButton(name = "Сохранить",
                        action = { viewModel.sendReview {navController.navigate("login_screen")} },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    SecondaryButton(name = "Очистить",
                        action = { viewModel.clearReview() },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        )
    }

    @Composable
    fun ReviewDialogContent() {
        Column {
            Text(
                text = "Оставить отзыв",
                color = Color.White,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                lineHeight = 32.sp,
                letterSpacing = 0.5.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))

            StarBlock(rating = viewModel.getReviewInputRating())
            Spacer(modifier = Modifier.height(10.dp))

            ReviewTextField(label = "Отзыв", value = viewModel.getReviewInputText()) { }
            Spacer(modifier = Modifier.height(16.dp))

            ReviewCheckboxContent(text = "Анонимный отзыв", checkboxState = viewModel.getReviewInputAnonymous())
        }
    }

    @Composable
    fun DescriptionText(text: String) {
        Text(
            text = text,
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun AboutText(
        text: String,
        isTitle: Boolean,
        align: TextAlign = TextAlign.Start,
        color: Color,
        modifier: Modifier = Modifier
    ) {
        Text(
            textAlign = align,
            modifier = modifier.defaultMinSize(minWidth = 100.dp),
            text = text,
            color = color,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            lineHeight = 15.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun CategoryText(text: String, modifier: Modifier = Modifier) {
        Text(
            modifier = modifier,
            text = text,
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            letterSpacing = 0.5.sp,
        )
    }
}
