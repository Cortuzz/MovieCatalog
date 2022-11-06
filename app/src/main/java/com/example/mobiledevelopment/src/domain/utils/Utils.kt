package com.example.mobiledevelopment.src.domain.utils

import android.annotation.SuppressLint
import android.content.res.Resources.NotFoundException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        private val genders = listOf("Мужчина", "Женщина")

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun parseGender(gender: Int): String {
            if (gender >= 2) return ""
            return genders[gender]
        }

        fun parseGenderToInt(gender: String): Int {
            if (gender !in genders) throw NotFoundException()
            return genders.indexOf(gender)
        }

        @SuppressLint("SimpleDateFormat")
        fun isValidDate(date: String): Boolean {
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

        fun formatDate(date: String?): String? {
            val sDate = date?.split('.')?.reversed()?.toMutableList()
            if (sDate.isNullOrEmpty() || sDate.size != 3) return null

            sDate[1] = sDate[1].padStart(2, '0')
            sDate[2] = sDate[2].padStart(2, '0')

            return sDate.joinToString("-")
        }
    }
}