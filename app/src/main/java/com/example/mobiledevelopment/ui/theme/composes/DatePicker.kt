package com.example.mobiledevelopment.ui.theme.composes

import android.app.DatePickerDialog
import android.view.ContextThemeWrapper
import android.widget.DatePicker
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobiledevelopment.R
import com.example.mobiledevelopment.src.utils.Utils
import java.util.*

@Composable
fun DatePicker(date: MutableState<String>, modifier: Modifier = Modifier) {
    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

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


    val mDatePickerDialog = DatePickerDialog(
        ContextThemeWrapper(mContext, R.style.Theme_MobileDevelopment_Calendar),
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            date.value = "$mDayOfMonth.${mMonth+1}.$mYear"
        }, mYear, mMonth, mDay
    )

    mDatePickerDialog.window?.setBackgroundDrawableResource(R.drawable.shape)

    Image(
        modifier = modifier.clickable {  mDatePickerDialog.show() }.offset(y = 2.dp),
        painter = painterResource(id = R.drawable.date_picker),
        contentDescription = "Date picker",
    )
}