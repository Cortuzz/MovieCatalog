package com.example.mobiledevelopment.ui.theme.composes

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.src.utils.Utils
import java.util.*

@Composable
fun DatePicker(date: MutableState<String>, modifier: Modifier = Modifier) {
    // Fetching the Local Context
    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    val parsedDate = Utils.tryParseDate(date.value)

    if (parsedDate.isNullOrEmpty()) {
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    } else {
        mYear = parsedDate[2]
        mMonth = parsedDate[1] - 1
        mDay = parsedDate[0]
    }

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth.${mMonth+1}.$mYear"
        }, mYear, mMonth, mDay
    )

    Image(
        modifier = modifier.clickable {  mDatePickerDialog.show() },
        painter = painterResource(id = R.drawable.date_picker),
        contentDescription = "Date picker",
    )
}