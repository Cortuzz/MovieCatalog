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
import com.example.mobiledevelopment.src.domain.composes.*
import com.example.mobiledevelopment.src.domain.main.mainText
import com.example.mobiledevelopment.src.domain.profile.*
import com.example.mobiledevelopment.ui.theme.*


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

@Composable
fun MainData() {
    if (viewModel.getProfileModel().value == null) return

    Column(modifier = Modifier
        .padding(bottom = 200.dp)
        .verticalScroll(rememberScrollState())) {

        for (field in viewModel.fields) {
            val changeIndicator =
                if (field.initialValue.value != field.value.value)
                    changeIndicatorText
                else ""

            Row {
                FieldLabel(text = field.label)
                FieldLabel(text = changeIndicator, color = AccentColor)
            }


            Spacer(Modifier.height(8.dp))
            ProfileInputText(field.value, isDate = field.isDate)
            Spacer(Modifier.height(2.dp))
            WrongFieldText(text = field.wrongText, correct = field.isCorrect)
            Spacer(Modifier.height(16.dp))
        }

        val genderChangeIndicator =
            if (viewModel.gender.initialValue.value != viewModel.gender.value.value)
                changeIndicatorText
            else ""

        Row {
            FieldLabel(text = genderText)
            FieldLabel(text = genderChangeIndicator, color = AccentColor)
        }

        Spacer(Modifier.height(8.dp))
        GenderField(viewModel.gender.value)
    }
}

@Composable
fun Buttons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 70.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        PrimaryButton(
            name = saveText,
            action = { viewModel.updateProfile { navigateToLogin() } },
            modifier = Modifier.fillMaxWidth()
        )

        SecondaryButton(
            name = logoutText,
            action = { viewModel.logout { navigateToLogin() } },
            modifier = Modifier.fillMaxWidth()
        )
    }

}


@Composable
fun ProfileScreen(navToLogin: () -> Unit) {
    navigateToLogin = navToLogin
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
