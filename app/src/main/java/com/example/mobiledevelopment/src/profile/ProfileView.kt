package com.example.mobiledevelopment.src.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.ui.theme.*
import com.example.mobiledevelopment.ui.theme.composes.*


private val viewModel = ProfileViewModel()

private lateinit var navigateToLogin: () -> Unit
private lateinit var navigateToMain: () -> Unit

@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(
            viewModel.getProfileModel().value?.avatarLink ?: "",
            modifier = Modifier
                .requiredSize(88.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = viewModel.getProfileModel().value?.nickName ?: "",
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            letterSpacing = 0.5.sp,
            color = Color.White,
            modifier = Modifier.offset(y = (-3).dp)
        )

    }
    Spacer(Modifier.height(16.dp))
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        fontFamily = IBMPlex,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        color = OutlineColor
    )
}

@Composable
fun MainData() {
    if (viewModel.getProfileModel().value == null) return

    Column(modifier = Modifier
        .padding(bottom = 200.dp)
        .verticalScroll(rememberScrollState())) {

        FieldLabel(text = "E-mail")
        Spacer(Modifier.height(8.dp))
        ProfileInputText(viewModel.getEmail())
        Spacer(Modifier.height(2.dp))
        WrongFieldText(text = "Некорректный E-mail", correct = viewModel.getEmailCorrectChecker())
        Spacer(Modifier.height(16.dp))

        FieldLabel(text = "Ссылка на аватарку")
        Spacer(Modifier.height(8.dp))
        ProfileInputText(viewModel.getAvatarLink())
        Spacer(Modifier.height(12.dp))

        FieldLabel(text = "Имя")
        Spacer(Modifier.height(8.dp))
        ProfileInputText(viewModel.getName())
        Spacer(Modifier.height(12.dp))

        FieldLabel(text = "Дата рождения")
        Spacer(Modifier.height(8.dp))
        ProfileInputText(viewModel.getBirthDate())
        Spacer(Modifier.height(2.dp))
        WrongFieldText(text = "Некорректная дата рождения", correct = viewModel.getBirthDateCorrectChecker())
        Spacer(Modifier.height(12.dp))

        FieldLabel(text = "Пол")
        Spacer(Modifier.height(8.dp))
        GenderField(viewModel.getGender())
    }
}

@Composable
fun Buttons() {
    Column(
        modifier = Modifier.fillMaxSize().padding(bottom = 70.dp).padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(
            name = "Сохранить",
            action = { viewModel.updateProfile { navigateToLogin() } },
            modifier = Modifier.fillMaxWidth()
        )

        SecondaryButton(
            name = "Выйти из аккаунта",
            action = { viewModel.logout { navigateToLogin() } },
            modifier = Modifier.fillMaxWidth()
        )
    }

}

@Composable
fun NavigationBlock() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
    ) {
        Row(
            modifier = Modifier
                .background(NavigationColor)
                .fillMaxWidth(),
        ) {
            NavigationButton(
                name = "Главное",
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.main_page),
                fraction = 0.5f,
            )
            NavigationButton(
                name = "Профиль",
                onClick = { /*TODO*/ },
                painter = painterResource(id = R.drawable.profile_page),
                fraction = 1f,
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
            modifier = Modifier.fillMaxWidth().background(NavigationColor),

            ) {
            NavigationButton(
                name = "Главное",
                onClick = { navigateToMain() },
                painter = painterResource(id = R.drawable.main_page),
                fraction = 0.5f,
            )
            NavigationButton(
                name = "Профиль",
                onClick = {  },
                painter = painterResource(id = R.drawable.profile_page),
                fraction = 1f,
            )
        }
    }
}

@Composable
fun ProfileScreen(navToLogin: () -> Unit, navToMain: () -> Unit) {
    navigateToLogin = navToLogin
    navigateToMain = navToMain
    Navigation()
    viewModel.getProfile {  }
    if (viewModel.getProfileModel().value == null) return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 40.dp)
            .padding(horizontal = 16.dp)
    ) {
        Header()
        MainData()
    }
    Buttons()
}
