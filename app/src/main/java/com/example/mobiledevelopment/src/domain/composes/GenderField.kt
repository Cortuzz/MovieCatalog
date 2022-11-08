package com.example.mobiledevelopment.src.domain.composes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiledevelopment.src.domain.registration.GenderViewData
import com.example.mobiledevelopment.src.domain.registration.ViewField
import com.example.mobiledevelopment.src.domain.registration.femaleOptionText
import com.example.mobiledevelopment.src.domain.registration.maleOptionText
import com.example.mobiledevelopment.ui.theme.*

@Composable
fun GenderField(value: MutableState<String>) {
    val selectedValue = rememberSaveable { value }
    val items = listOf(
        GenderViewData(maleOptionText, RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp), 0.5f, 1.dp),
        GenderViewData(femaleOptionText, RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp), 1f, 0.dp)
    )

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (GenderViewData) -> Unit = {
        selectedValue.value = it.name
        value.value = it.name
    }
    MainBlock(onChangeState, isSelectedItem, items,
        Modifier.fillMaxWidth().padding(end = 1.dp)
    )
}

@Composable
fun GenderField(onSelect: (ViewField, String) -> Unit) {
    val selectedValue = rememberSaveable { mutableStateOf("") }
    val items = listOf(
        GenderViewData(maleOptionText, RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp), 0.5f, 1.dp),
        GenderViewData(femaleOptionText, RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp), 1f, 0.dp)
    )

    val isSelectedItem: (String) -> Boolean = { selectedValue.value == it }
    val onChangeState: (GenderViewData) -> Unit = {
        selectedValue.value = it.name
        onSelect(ViewField.Gender, items.indexOf(it).toString())
    }
    MainBlock(onChangeState, isSelectedItem, items,
        Modifier.fillMaxWidth().padding(start = 15.dp, end = 16.dp)
    )
}

@Composable
private fun MainBlock(
    onChangeState: (GenderViewData) -> Unit,
    isSelectedItem: (String) -> Boolean,
    items: List<GenderViewData>,
    modifier: Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
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