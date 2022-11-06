package com.example.mobiledevelopment.src.domain.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat

class DateProviderService {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun isValid(date: String): Boolean {
            val dateFormat = "dd.MM.yyyy"

            return try {
                val s = date.split(".")
                if (s.size != 3)
                    return false

                val df: DateFormat = SimpleDateFormat(dateFormat)
                df.isLenient = false
                df.parse(date)
                true
            } catch (_: Exception) {
                false
            }
        }

        fun isValidBirth(date: String): Boolean {
            if (!isValid(date))
                return false

            val y = date.split(".")[2].toInt()

            if (y < 1900 || y > 2020)
                return false

            return true
        }

        fun tryParse(date: String): List<Int>? {
            if (!isValid(date))
                return null

            val lDate = date.split(".").toMutableList()
            val outDate = mutableListOf<Int>()

            lDate.forEach { el -> outDate.add(el.toInt()) }
            return outDate
        }


        fun parseTimestamp(value: String): String {
            val date = value.split("T")[0]
            return date.split("-").reversed().joinToString(".")
        }

        fun format(date: String?): String? {
            val sDate = date?.split('.')?.reversed()?.toMutableList()
            if (sDate.isNullOrEmpty() || sDate.size != 3) return null

            sDate[1] = sDate[1].padStart(2, '0')
            sDate[2] = sDate[2].padStart(2, '0')

            return sDate.joinToString("-")
        }
    }
}