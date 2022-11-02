package com.example.mobiledevelopment.src.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.login.domain.*
import com.example.mobiledevelopment.ui.theme.composes.*

class LoginView(private val navController: NavHostController): Drawable {
    private var viewModel: LoginViewModel = LoginViewModel()


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
    override fun Draw() {
        LoginFailDialog()

        Column(
            modifier = Modifier.padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Logo(Modifier, Alignment.Center)
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
    fun Fields() {
        Column(modifier = Modifier.padding(top = 48.dp)) {
            InputText(
                label = loginText,
                value = viewModel.getField(ViewField.Login),
                {value -> viewModel.changeField(ViewField.Login, value)},

            )
            Spacer(Modifier.height(14.4.dp))
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
                navController.navigate("main_screen") {
                    popUpTo(0)
                }
            } },
                isEnabled = viewModel.fullness)
            Spacer(Modifier.height(8.dp))
            SecondaryButton(name = goToRegisterText, action = {
                navController.navigate("registration_screen") {
                    popUpTo(0)
                }
            })
        }
    }
}