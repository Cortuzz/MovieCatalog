package com.example.mobiledevelopment.src.domain.utils

import android.content.res.Resources

class GenderProviderService {
    companion object {
        private val genders = listOf("Мужчина", "Женщина")

        fun parse(gender: Int): String {
            if (gender >= 2) return ""
            return genders[gender]
        }

        fun parseToInt(gender: String): Int {
            if (gender !in genders) throw Resources.NotFoundException()
            return genders.indexOf(gender)
        }
    }
}