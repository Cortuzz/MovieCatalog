package com.example.mobiledevelopment.src.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.src.domain.admin.AddMovieButtonText
import com.example.mobiledevelopment.src.domain.admin.AddMovieText
import com.example.mobiledevelopment.src.domain.admin.GenresText
import com.example.mobiledevelopment.src.domain.composes.Logo
import com.example.mobiledevelopment.src.domain.composes.PrimaryButton
import com.example.mobiledevelopment.src.domain.composes.ProfileInputText
import com.example.mobiledevelopment.src.domain.composes.WrongFieldText
import com.example.mobiledevelopment.src.domain.models.GenreModel
import com.example.mobiledevelopment.src.domain.utils.noRippleClickable
import com.example.mobiledevelopment.src.main.CategoryText
import com.example.mobiledevelopment.src.movie.AboutText
import com.example.mobiledevelopment.ui.theme.AccentColor
import com.example.mobiledevelopment.ui.theme.ActiveColor
import com.example.mobiledevelopment.ui.theme.IBMPlex
import com.example.mobiledevelopment.ui.theme.OutlineColor
import com.google.accompanist.flowlayout.FlowRow


private val viewModel = AdminViewModel()

@Composable
fun AdminScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 54.dp)
        ,
    ) {
        Logo(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            alignment = Alignment.TopCenter
        )
        MainData()
    }
}

@Composable
fun MainData() {
    CategoryText(name = AddMovieText)
    Spacer(Modifier.height(8.dp))
    MovieFields()
}

@Composable
fun MovieFields() {

    LazyColumn {
        items(viewModel.fields.fields.size) {
            val field = viewModel.fields.fields[it]

            FieldLabel(text = field.label)
            Spacer(Modifier.height(8.dp))
            ProfileInputText(field.value, isNumber = field.isInt)
            Spacer(Modifier.height(16.dp))
        }

        item { Genres() }
        item { Spacer(Modifier.height(24.dp)) }
        item { PrimaryButton(name = AddMovieButtonText, action = { viewModel.addMovie() }) }
        item { Spacer(Modifier.height(16.dp)) }
    }
}

@Composable
fun Genres() {
    FieldLabel(text = GenresText)

    FlowRow {
        for (genre in viewModel.fields.availableGenres) {
            GenreBlock(text = genre.name ?: "", genre)
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun GenreBlock(text: String, genreModel: GenreModel) {
    val color = remember { mutableStateOf(AccentColor) }

    Box(
        modifier = Modifier
            .padding(top = 8.dp)
            .requiredHeight(height = 35.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = color.value)
            .wrapContentWidth()
            .noRippleClickable {
                genreModel.isActive = !genreModel.isActive
                color.value = if (genreModel.isActive) ActiveColor else AccentColor
            }
    ) {
        Text(
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSize(minWidth = 100.dp).offset(y = 6.dp),
            text = text,
            color = Color.White,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            lineHeight = 15.sp,
            letterSpacing = 0.5.sp,
        )
    }
}

@Composable
fun FieldLabel(text: String, color: Color = OutlineColor) {
    Text(
        text = text,
        fontFamily = IBMPlex,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        color = color
    )
}