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
import com.example.mobiledevelopment.src.domain.Drawable
import com.example.mobiledevelopment.src.MainActivity
import com.example.mobiledevelopment.src.registration.domain.GenderViewData
import com.example.mobiledevelopment.src.registration.domain.ViewField
import com.example.mobiledevelopment.ui.theme.composes.InputText
import com.example.mobiledevelopment.ui.theme.composes.Logo
import com.example.mobiledevelopment.ui.theme.composes.PrimaryButton
import com.example.mobiledevelopment.ui.theme.composes.SecondaryButton
import com.example.mobiledevelopment.ui.theme.*

class RegistrationView(activity: MainActivity): Drawable {
    companion object {
        private var instance: RegistrationView? = null

        fun getInstance(activity: MainActivity): RegistrationView {
            if (instance == null)
                instance = RegistrationView(activity)

            return instance as RegistrationView
        }
    }

    private val viewModel: RegistrationViewModel = RegistrationViewModel(this, activity)

    @Composable
    override fun Draw() {
        Column(                            // todo
            modifier = Modifier
                .padding(top = 56.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Logo(
                Modifier
                    .animateContentSize()
                    .size(150.dp), Alignment.Center)

            Label()
            Fields()

            Buttons()
        }
    }

    @Composable
    fun Label() {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Регистрация",
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
        // todo
        val names = listOf("Логин", "E-mail", "Имя", "Пароль", "Подтвердите пароль", "Дата рождения")
        val enums = listOf(ViewField.Login, ViewField.Email, ViewField.Name, ViewField.Password, ViewField.RepeatPassword, ViewField.DateOfBirth)
        val hidden = listOf(false, false, false, true, true, false)

        Column(modifier = Modifier.padding(top = 16.dp)) {

            for (i in 0 until(6)) {
                InputText(
                    label = names[i],
                    viewModel.getField(enums[i]),
                    { value -> viewModel.changeField(enums[i], value)  },
                    isHidden = hidden[i]
                )
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
            GenderViewData("Мужчина", RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp), 0.5f, 1.dp),
            GenderViewData("Женщина", RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp), 1f, 0.dp)
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
            PrimaryButton(name = "Зарегистрироваться", action = { viewModel.handleRegistrationClick() }, isEnabled = viewModel.fullness)
            Spacer(Modifier.height(8.dp))
            SecondaryButton(name = "У меня уже есть аккаунт", action = { viewModel.handleLoginViewClick() })
        }

        if (viewModel.fullness.value) {
            AlertDialog(
                onDismissRequest = {
                viewModel.fullness.value = false
            },
                title = {
                    Text(text = "Dialog Title")
                },
                text = {
                    Text("Here is a text ")
                },
                confirmButton = {
                    Button(

                        onClick = {
                            viewModel.fullness.value = false
                        }) {
                        Text("This is the Confirm Button")
                    }
                },
                dismissButton = {
                    Button(

                        onClick = {
                            viewModel.fullness.value = false
                        }) {
                        Text("This is the dismiss Button")
                    }
                })
        }
    }
}