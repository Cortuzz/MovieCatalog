package com.example.mobiledevelopment.ui.theme.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.ui.theme.*

@Composable
fun StarBlock(rating: MutableState<Int>) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        for (i in 0 until 10) {
            Star(i, rating)
        }
    }
}

@Composable
fun Star(index: Int, rating: MutableState<Int>) {
    Box(
        modifier = Modifier.requiredSize(24.dp)
    ) {
        if (rating.value > index) {
            Image(
                painter = painterResource(id = R.drawable.star_bg),
                contentDescription = null,
                modifier = Modifier.offset(x = (-3).dp, y = (-4).dp)
            )

            Image(
                painter = painterResource(R.drawable.filled_star),
                contentDescription = "Filled star",
                modifier =  Modifier.clickable { rating.value = index + 1 }
            )
            return@Box
        }

        Image(
            painter = painterResource(R.drawable.star),
            contentDescription = "Star",
            modifier = Modifier
                .offset(y = (-1).dp)
                .clickable { rating.value = index + 1 }
        )
    }
}

@Composable
fun ReviewTextField(label: String, value: MutableState<String>, onChange: () -> Unit) {
    var focusState by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        placeholder = {
            Text(
                text = if (focusState) "" else label,
                fontFamily = IBMPlex,
                fontWeight = FontWeight.Medium,
                fontSize = 13.7.sp,
                lineHeight = 17.sp,
                modifier = Modifier.offset(y = (-3).dp),
                color = ReviewLabelColor
            )
        },

        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focus -> focusState = focus.isFocused }
            .height(120.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            backgroundColor = Color.White,
            textColor = BackgroundColor,
            focusedBorderColor = OutlineColor,
            unfocusedBorderColor = DialogColor
        ),
        value = value.value,
        onValueChange = { value.value = it; onChange() },

        shape = RoundedCornerShape(8.dp)
    )
}

@Composable
fun ReviewCheckboxContent(text: String, checkboxState: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            fontFamily = IBMPlex,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.White
        )

        ReviewCheckbox(checkboxState = checkboxState)
//        Checkbox(
//            checked = checkboxState.value,
//            onCheckedChange = { checkboxState.value = it },
//            colors = CheckboxDefaults.colors(
//                uncheckedColor = Color.White,
//                checkmarkColor = AccentColor
//            )
//        )
    }
}

@Composable
fun ReviewCheckbox(checkboxState: MutableState<Boolean>) {
    Row {
        Card(
            modifier = Modifier.background(Color.Transparent),
            elevation = 0.dp,
            shape = RoundedCornerShape(6.dp),
            border = BorderStroke(1.5.dp, color = CheckboxOutlineColor)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(DialogColor)
                    .clickable {
                        checkboxState.value = !checkboxState.value
                    },
                contentAlignment = Center
            ) {
                if(checkboxState.value)
                    Icon(
                        Icons.Default.Check,
                        modifier = Modifier.requiredWidth(20.dp),
                        contentDescription = "",
                        tint = AccentColor
                    )
            }
        }
    }
}