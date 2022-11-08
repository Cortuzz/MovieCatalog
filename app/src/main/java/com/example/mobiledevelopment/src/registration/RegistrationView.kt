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
        title = errorTitleText,
        text = when (viewModel.registrationState.value) {
            RegistrationState.Error -> errorNoInternetConnectionText
            RegistrationState.UserExist -> errorDuplicateUserText
            else -> internalErrorText
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
    Column(modifier = Modifier
        .padding(top = 16.dp, bottom = 132.dp)
        .verticalScroll(rememberScrollState())) {
        for (field in viewFieldModels) {
            InputText(
                label = field.name,
                viewModel.getMutableState(field.viewField),
                onChange = { viewModel.checkFullness() },
                isHidden = field.isHidden,
                isDate = field.viewField == ViewField.DateOfBirth
            )

            Spacer(Modifier.height(2.dp))
            WrongFieldText(
                text = field.errorText,
                correct = viewModel.isFieldCorrect(field.viewField)
            )
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