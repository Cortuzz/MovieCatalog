package com.example.mobiledevelopment.src.registration

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.src.domain.composes.*
import com.example.mobiledevelopment.src.domain.registration.*
import com.example.mobiledevelopment.src.domain.registration.RegistrationState
import com.example.mobiledevelopment.ui.theme.*

private val viewModel: RegistrationViewModel = RegistrationViewModel()
private lateinit var navigateToMain: () -> Unit
private lateinit var navigateToLogin: () -> Unit

@Composable
fun RegistrationScreen(navToMain: () -> Unit, navToLogin: () -> Unit) {
    viewModel.restoreSize()
    navigateToLogin = navToLogin
    navigateToMain = navToMain

    RegistrationContent()
}

@Composable
fun RegistrationContent() {
    RegistrationFailDialog()

    val size by animateDpAsState(
        targetValue = viewModel.logoSize.value,
        animationSpec = tween(1000),
        finishedListener = {
            navigateToLogin()
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingIndicator(
            Modifier.requiredSize(100.dp)
        ) { viewModel.registrationState.value == RegistrationState.Loading }
    }

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(top = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Logo(Modifier.height(size), Alignment.Center)

        Label()
        Fields()
    }

    Buttons()
}

@Composable
fun RegistrationFailDialog() {
    if (viewModel.registrationState.value in listOf(RegistrationState.Idle, RegistrationState.Loading))
        return

    Dialog(
        title = "Регистрация отклонена",
        text = when (viewModel.registrationState.value) {
            RegistrationState.Error -> "Нет подключения к интернету или сервер недоступен"
            RegistrationState.UserExist -> "Пользователь с таким логином уже существует"
            else -> "Произошла внутренняя ошибка приложения, отчет об ошибке отправлен разработчикам"
        },
        onDismissRequest = {
            viewModel.registrationState.value = RegistrationState.Idle
        }
    )
}

@Composable
fun Label() {
    Row(
        modifier = Modifier.fillMaxWidth().requiredHeight(32.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = registrationText,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            letterSpacing = 0.5.sp,
            color = AccentColor,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun Fields() {
    // TODO
    val names = listOf(loginText, emailText, nameText, passwordText, repeatPasswordText, birthDateText)
    val enums = listOf(ViewField.Login, ViewField.Email, ViewField.Name, ViewField.Password, ViewField.RepeatPassword, ViewField.DateOfBirth)
    val hidden = listOf(false, false, false, true, true, false)
    val text = listOf(
        "Логин слишком короткий",
        "Некорректный E-mail",
        "Имя слишком короткое",
        "Ненадежный пароль",
        "Пароли не совпадают",
        "Неверная дата рождения"
    )

    Column(modifier = Modifier
        .padding(top = 16.dp, bottom = 132.dp)
        .verticalScroll(rememberScrollState())) {
        for (i in 0 until(6)) {
            InputText(
                label = names[i],
                viewModel.getMutableState(enums[i]),
                onChange = { viewModel.checkFullness() },
                isHidden = hidden[i],
                isDate = enums[i] == ViewField.DateOfBirth
            )

            Spacer(Modifier.height(2.dp))
            WrongFieldText(text = text[i], correct = viewModel.isFieldCorrect(enums[i]))
            Spacer(Modifier.height(14.dp))
        }

        GenderField { field, str -> viewModel.changeField(field, str) }
        Spacer(Modifier.height(32.dp))
    }
}


@Composable
fun Buttons() {
    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxHeight()
    ) {
        PrimaryButton(name = registerText, action = {
            viewModel.handleRegistrationClick { navigateToMain() } },
            isEnabled = viewModel.fullness)
        Spacer(Modifier.height(8.dp))
        SecondaryButton(name = goToLoginText, action = { viewModel.handleLoginScreenClick() })
    }
}