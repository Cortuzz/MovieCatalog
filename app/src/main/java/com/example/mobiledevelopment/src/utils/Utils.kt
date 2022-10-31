package com.example.mobiledevelopment.src.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun isValidDate(date: String): Boolean {
            val dateFormat = "dd.MM.yyyy";

            return try {
                val s = date.split(".")
                if (s.size != 3)
                    return false

                val df: DateFormat = SimpleDateFormat(dateFormat)
                df.setLenient(false)
                df.parse(date)
                true
            } catch (_: Exception) {
                false
            }
        }

        fun isValidBirthDate(date: String): Boolean {
            if (!isValidDate(date))
                return false

            val y = date.split(".")[2].toInt()

            if (y < 1900 || y > 2020)
                return false

            return true
        }

        fun tryParseDate(date: String): List<Int>? {
            if (!isValidDate(date))
                return null

            val lDate = date.split(".").toMutableList()
            val outDate = mutableListOf<Int>()

            lDate.forEach { el -> outDate.add(el.toInt()) }
            return outDate
        }

        fun parseMoney(value: Int?): String? {
            if (value == null) return null
            return "$" + String.format(Locale.US, "%,d", value).replace(",", " ")
        }

        fun parseTimestamp(value: String): String {
            val date = value.split("T")[0]
            return date.split("-").reversed().joinToString(".")
        }
    }
}