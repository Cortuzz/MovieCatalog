package com.example.mobiledevelopment.src.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.domain.retrofit.ProfileModel
import com.example.mobiledevelopment.src.utils.Utils

class ProfileViewModel {
    private val repository = ProfileRepository()
    private val profileModel: MutableState<ProfileModel?> = mutableStateOf(null)

    private val email = mutableStateOf("")
    private val avatarLink = mutableStateOf("")
    private val name = mutableStateOf("")
    private val birthDate = mutableStateOf("")
    private val gender = mutableStateOf("")

    private val isEmailCorrect = mutableStateOf(true)
    private val isBirthDateCorrect = mutableStateOf(true)

    fun getProfile(onUnauthorized: () -> Unit) {
        repository.getProfile(
            onFailureAction = {

            },
            onBadResponseAction = {
                if (it == 401) onUnauthorized()
            },
            onResponseAction = {
                parseModel(it)
            }
        )
    }

    fun updateProfile(onUnauthorized: () -> Unit) {
        val model = parseModelForServer() ?: return

        repository.updateProfile(
            onFailureAction = {

            },
            onBadResponseAction = {
                if (it == 401) onUnauthorized()
            },
            onResponseAction = {

            },
            profileModel = model
        )
    }

    fun logout(action: () -> Unit) {
        repository.logout(action)
    }

    private fun parseModelForServer(): ProfileModel? {
        val model = profileModel.value ?: return null
        if (!checkCorrect()) return null

        model.avatarLink = avatarLink.value
        model.gender = Utils.parseGenderToInt(gender.value)
        model.name = name.value
        model.email = email.value
        model.birthDate = Utils.formatDate(birthDate.value)

        return model
    }

    private fun parseModel(profile: ProfileModel) {
        profileModel.value = profile

        email.value = profile.email
        name.value = profile.name
        avatarLink.value = profile.avatarLink ?: ""

        if (profile.birthDate != null)
            birthDate.value = Utils.parseTimestamp(profile.birthDate!!)
        if (profile.gender != null)
            gender.value = Utils.parseGender(profile.gender!!)

        checkCorrect()
    }

    fun getGender(): MutableState<String> {
        return gender
    }

    fun getEmail(): MutableState<String> {
        return email
    }

    fun getAvatarLink(): MutableState<String> {
        return avatarLink
    }

    fun getName(): MutableState<String> {
        return name
    }

    fun getBirthDate(): MutableState<String> {
        return birthDate
    }

    fun getEmailCorrectChecker(): MutableState<Boolean> {
        return isEmailCorrect
    }

    fun getBirthDateCorrectChecker(): MutableState<Boolean> {
        return isBirthDateCorrect
    }

    private fun checkCorrect(): Boolean {
        isEmailCorrect.value = checkEmail(email.value)
        isBirthDateCorrect.value = checkDate(birthDate.value)

        if (checkEmail(email.value) && checkDate(birthDate.value))
            return true
        return false
    }

    fun getProfileModel(): MutableState<ProfileModel?> {
        return profileModel
    }

    private fun checkEmail(email: String): Boolean {
        return Utils.isValidEmail(email)
    }

    private fun checkDate(date: String): Boolean {
        return Utils.isValidBirthDate(date)
    }
}