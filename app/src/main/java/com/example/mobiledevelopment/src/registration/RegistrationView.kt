package com.example.mobiledevelopment.src.registration

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.login.domain.AuthState
import com.example.mobiledevelopment.src.main.domain.RegistrationState
import com.example.mobiledevelopment.src.registration.domain.*
import com.example.mobiledevelopment.ui.theme.*
import com.example.mobiledevelopment.ui.theme.composes.*

class RegistrationView(private val navController: NavHostController): Drawable {
    private val viewModel: RegistrationViewModel = RegistrationViewModel()

    @Composable
    override fun Draw() {
        RegistrationFailDialog()

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
            modifier = Modifier
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Logo(
                Modifier.size(150.dp), Alignment.Center)

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
    fun WrongFieldText(text: String, correct: MutableState<Boolean>) {
        if (correct.value)
            return

        Text(
            modifier = Modifier.padding(horizontal = 18.dp),
            text = text,
            color = AccentColor,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 0.5.sp,
        )
    }

    @Composable
    fun Label() {
        Row(
            modifier = Modifier.fillMaxWidth(),
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
                Spacer(Modifier.height(16.dp))
            }

            GenderField()
            Spacer(Modifier.height(32.dp))
        }
    }

    @Composable
    fun GenderField() {
        val selectedValue = rememberSaveable { mutableStateOf("") }
        val items = listOf(
            GenderViewData(maleOptionText, RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp), 0.5f, 1.dp),
            GenderViewData(femaleOptionText, RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp), 1f, 0.dp)
        )

        val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
        val onChangeState: (GenderViewData) -> Unit = {
            selectedValue.value = it.name
            viewModel.changeField(ViewField.Gender, items.indexOf(it).toString())
        }

        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 16.dp)
        ) {


            items.forEach { item ->
                OutlinedButton(
                    onClick = { onChangeState(item) },
                    modifier = Modifier
                        .fillMaxWidth(item.fraction)
                        .offset(x = item.offset)
                        .selectable(
                            selected = isSelectedItem(item.name),
                            onClick = { onChangeState(item) },
                            role = Role.RadioButton
                        ),

                    enabled = !isSelectedItem(item.name),
                    colors = getRadioButtonColor(),
                    border = BorderStroke(1.dp, OutlineColor),
                    shape = item.shape
                ) {
                    Text(
                        text = item.name,
                        fontFamily = IBMPlex,
                        fontWeight = FontWeight(400),
                        fontSize = 14.sp,
                        lineHeight = 18.sp,
                        letterSpacing = 0.5.sp,
                        modifier = Modifier.padding(top = 1.dp, bottom = 5.dp)
                    )
                }
            }
        }

    }
    
    @Composable
    private fun getRadioButtonColor(): ButtonColors {
        return ButtonDefaults.buttonColors(
            contentColor = TextColor,
            backgroundColor = Color.Transparent,
            disabledContentColor = SelectedTextColor,
            disabledBackgroundColor = AccentColor
        )
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
                viewModel.handleRegistrationClick {
                    navController.navigate("main_screen")
                } },
                isEnabled = viewModel.fullness)
            Spacer(Modifier.height(8.dp))
            SecondaryButton(name = goToLoginText, action = { navController.navigate("login_screen") })
        }
    }
}