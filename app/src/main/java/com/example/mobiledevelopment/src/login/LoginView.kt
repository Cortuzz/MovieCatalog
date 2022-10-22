package com.example.mobiledevelopment.src.login

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.login.domain.*
import com.example.mobiledevelopment.src.registration.RegistrationView
import com.example.mobiledevelopment.ui.theme.composes.InputText
import com.example.mobiledevelopment.ui.theme.composes.Logo
import com.example.mobiledevelopment.ui.theme.composes.PrimaryButton
import com.example.mobiledevelopment.ui.theme.composes.SecondaryButton

class LoginView(activity: MainActivity): Drawable {
    companion object {
        private var instance: LoginView? = null

        fun getInstance(activity: MainActivity): LoginView {
            if (instance == null)
                instance = LoginView(activity)

            return instance as LoginView
        }
    }

    private var viewModel: LoginViewModel = LoginViewModel(this, activity)


    @Composable
    override fun Draw() {
        Column(
            modifier = Modifier.padding(top = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Logo(Modifier, Alignment.Center)
            Fields()

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
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxHeight()
        ) {
            PrimaryButton(name = signInText, action = { viewModel.handleLoginClick() }, isEnabled = viewModel.fullness)
            Spacer(Modifier.height(8.dp))
            SecondaryButton(name = goToRegisterText, action = { viewModel.handleRegistrationViewClick() })
        }
    }
}