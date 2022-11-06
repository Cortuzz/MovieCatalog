package com.example.mobiledevelopment.src.login

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.login.domain.*
import com.example.mobiledevelopment.ui.theme.composes.*

private var viewModel: LoginViewModel = LoginViewModel()
private lateinit var navigateToMain: () -> Unit
private lateinit var navigateToRegistration: () -> Unit


@Composable
fun LoginFailDialog() {
    if (viewModel.authState.value in listOf(AuthState.Idle, AuthState.Loading))
        return

    Dialog(
        title = "Авторизация отклонена",
        text = if (viewModel.authState.value == AuthState.Error)
            "Нет подключения к интернету или сервер недоступен"
            else "Введены некорректные данные",
        onDismissRequest = {
            viewModel.authState.value = AuthState.Idle
        }
    )
}

@Composable
fun LoginContent() {
    LoginFailDialog()

    val size by animateDpAsState(
        targetValue = viewModel.logoSize.value,
        animationSpec = tween(1000),
        finishedListener = {
            navigateToRegistration()
        }
    )

    Column(
        modifier = Modifier
            .padding(top = 56.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Logo(Modifier
            .height(size),
            Alignment.Center
        )
        Fields()
        LoadingIndicator(
            Modifier
                .requiredSize(100.dp)
                .padding(top = 20.dp)
        ) { viewModel.authState.value == AuthState.Loading }
        Buttons()
    }
}

@Composable
fun LoginScreen(navToMain: () -> Unit, navToRegistration: () -> Unit) {
    viewModel.restoreSize()

    navigateToRegistration = navToRegistration
    navigateToMain = navToMain
    LoginContent()
}

@Composable
fun Fields() {
    Column(modifier = Modifier.padding(top = 48.dp)) {
        InputText(
            label = loginText,
            value = viewModel.getField(ViewField.Login),
            {value -> viewModel.changeField(ViewField.Login, value)},

        )
        Spacer(Modifier.height(14.dp))
        InputText(
            label = passwordText,
            value = viewModel.getField(ViewField.Password),
            {value -> viewModel.changeField(ViewField.Password, value)},
            isHidden = true
        )
    }
}

@Composable
fun Buttons() {
    Column(
        modifier = Modifier
            .padding(bottom = 16.dp)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(name = signInText, action = { viewModel.handleLoginClick {
            navigateToMain()
        } },
            isEnabled = viewModel.fullness)
        Spacer(Modifier.height(8.dp))
        SecondaryButton(name = goToRegisterText, action = {
            viewModel.handleRegistrationScreenClick()
        })
    }
}