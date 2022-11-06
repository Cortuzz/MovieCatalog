package com.example.mobiledevelopment.src.domain.utils

import android.annotation.SuppressLint
import android.content.res.Resources.NotFoundException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun parseMoney(value: Int?): String? {
            if (value == null) return null
            return "$" + String.format(Locale.US, "%,d", value).replace(",", " ")
        }
    }
}