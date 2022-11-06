package com.example.mobiledevelopment.src.movie

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.src.domain.composes.*
import com.example.mobiledevelopment.src.domain.models.ReviewModel
import com.example.mobiledevelopment.src.domain.utils.services.DateProviderService
import com.example.mobiledevelopment.src.domain.utils.Utils
import com.example.mobiledevelopment.src.domain.utils.noRippleClickable
import com.example.mobiledevelopment.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow



private var viewModel = MovieViewModel()
private lateinit var navigateToMain: () -> Unit
private lateinit var navigateToLogin: () -> Unit

@Composable
fun HeaderPopup(value: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (value) 1f else 0f,
        animationSpec = tween(1000)
    )

    Row(
        modifier = Modifier
            .offset(y = 36.dp)
            .padding(start = 20.dp, end = 18.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BackButton()
        FavouriteButton()
    }


    Box(
        modifier = Modifier.alpha(alpha)
    ) {
        Row(
            modifier = Modifier
                .requiredHeight(80.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(16.dp))
                .background(BackgroundColor)
                .padding(top = 36.dp)
                .padding(start = 20.dp, end = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.requiredSize(312.dp, 32.dp)
            ){
                BackButton()
                Spacer(modifier = Modifier.width(13.dp))
                TitleText(
                    text = viewModel.getMovieModel().value?.name ?: "",
                )
            }

            Spacer(Modifier.width(10.dp))
            FavouriteButton()
        }
    }

}

@Composable
fun BackButton() {
    Image(
        painter = painterResource(id = R.drawable.back_button),
        contentDescription = "Back button",
        modifier = Modifier
            .offset(y = 10.dp)
            .noRippleClickable { navigateToMain() }
    )
}

@Composable
fun FavouriteButton() {
    Image(
        painter = painterResource(id = R.drawable.heart),
        contentDescription = "Favourite button",
        modifier = Modifier
            .offset(y = 10.dp)
            .noRippleClickable { }
    )
}

@Composable
fun Header(headerState: Boolean) {
    val movieModel = viewModel.getMovieModel().value ?: return

    Box(modifier = Modifier.requiredHeight(250.dp)) {
        MoviePoster(
            url = movieModel.poster ?: "",
            modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(250.dp)
                .clip(shape = RoundedCornerShape(16.dp)),
            contentScale = ContentScale.FillWidth,
            showLoading = false
        )

        LabelText(text = movieModel.name ?: "", headerState)
    }
}

@Composable
fun LabelText(text: String, value: Boolean) {
    val alpha by animateFloatAsState(
        targetValue = if (value) 1f else 0f,
        animationSpec = tween(1000)
    )

    Column(
        modifier = Modifier.fillMaxHeight().alpha(alpha),
        verticalArrangement = Arrangement.Bottom
    ) {
        Text(
            text = text,
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = IBMPlex,
            lineHeight = 40.sp,
            color = Color.White,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
    }
}

@Composable
fun Description() {
    val description = viewModel.getMovieModel().value?.description ?: return
    DescriptionText(text = description)
}

@Composable
fun About() {
    val movieModel = viewModel.getMovieModel().value ?: return

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
            color = Color(0xFFB3B3B3)
        )

        Spacer(modifier = Modifier.width(8.dp))

        AboutText(
            text = if (value.isNullOrBlank()) "Нет информации" else value,
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
        AboutText(text = text, align = TextAlign.Center, Color.White, modifier = Modifier.offset(y = 3.dp))
    }
}

@Composable
fun Genres() {
    val movieModel = viewModel.getMovieModel().value ?: return

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
fun ReviewHeader(review: ReviewModel) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Avatar(
                url = if (review.isAnonymous) "" else review.author?.avatar ?: "",
                Modifier
                    .requiredSize(40.dp)
                    .clip(CircleShape))

            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryText(
                    text = if (review.isAnonymous) "Анонимный пользователь" else review.author?.nickName ?: "Анонимный пользователь",
                    modifier = Modifier.offset(y = (-1).dp)
                )

                if (viewModel.compareIds(review.author?.userId ?: ""))
                    AboutText(text = "мой отзыв", color = OutlineColor)
            }

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
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .requiredHeight(24.dp)
    ) {
        AboutText(
            text = DateProviderService.parseTimestamp(review.createDateTime),
            color = Color(0xFFB7B7B7),
            modifier = Modifier.offset(y = 5.dp)
        )
        if (viewModel.compareIds(review.author?.userId ?: ""))
            EditReviewBlock(review)
    }
}

@Composable
fun EditReviewBlock(review: ReviewModel) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.edit_review_button),
            contentDescription = "Edit review",
            modifier = Modifier.noRippleClickable {  }
        )

        Spacer(Modifier.width(8.dp))

        Image(
            painter = painterResource(id = R.drawable.delete_review_button),
            contentDescription = "Delete review",
            modifier = Modifier.noRippleClickable {
                viewModel.deleteReview(review.id)
                { navigateToLogin() }
            }
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
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
}

@Composable
fun AddReviewButton() {
    if (viewModel.getClientReviewState().value)
        return

    Image(
        painter = painterResource(id = R.drawable.plus),
        contentDescription = "Добавить",
        modifier = Modifier.noRippleClickable { viewModel.getReviewDialogState().value = true }
    )
}

@Composable
fun Reviews() {
    val reviews = viewModel.getReviewList()

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

        for (review in reviews) {
            Review(review = review)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MainContent() {
    if (viewModel.getMovieModel().value == null)
        return

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Description()
        Spacer(modifier = Modifier.height(16.dp))

        About()
        Spacer(modifier = Modifier.height(16.dp))

        Genres()
        Spacer(modifier = Modifier.height(16.dp))

        Reviews()
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MovieScreen(navToLogin: () -> Unit, navToMain: () -> Unit) {
    viewModel.getMovie()
    navigateToLogin = navToLogin
    navigateToMain = navToMain

    if (viewModel.getReviewDialogState().value)
        ReviewDialog { viewModel.getReviewDialogState().value = false }

    val scrollState = rememberLazyListState()
    val value = remember { derivedStateOf { scrollState.firstVisibleItemIndex } }.value != 0 ||
            remember { derivedStateOf { scrollState.firstVisibleItemScrollOffset } }.value > 0

    LazyColumn(
        state = scrollState
    ) {
        item { Header(!value) }
        item { Spacer(modifier = Modifier.height(15.3.dp)) }
        item { MainContent() }
    }

    HeaderPopup(value)
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
                    action = {
                        viewModel.sendReview { navigateToLogin() }
                        viewModel.getReviewDialogState().value = false
                     },
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
fun TitleText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text,
        color = Color.White,
        fontFamily = IBMPlex,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
        letterSpacing = 0.5.sp,
        modifier = modifier
    )
}

@Composable
fun ReviewDialogContent() {
    Column {
        TitleText(text = "Оставить отзыв", Modifier.padding(bottom = 16.dp))
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