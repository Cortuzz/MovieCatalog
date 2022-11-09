package com.example.mobiledevelopment.src.profile

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.mobiledevelopment.src.domain.models.ProfileModel
import com.example.mobiledevelopment.src.domain.profile.*
import com.example.mobiledevelopment.src.domain.utils.services.DateProviderService
import com.example.mobiledevelopment.src.domain.utils.services.GenderProviderService
import com.example.mobiledevelopment.src.domain.utils.Utils

class ProfileViewModel {
    private val repository = ProfileRepository()
    private val profileModel: MutableState<ProfileModel?> = mutableStateOf(null)

    private val email = ViewFieldProvider(label = emailText, wrongText = emailWrongText)
    private val avatarLink = ViewFieldProvider(label = avatarLinkText)
    private val name = ViewFieldProvider(label = nameText)
    private val birthDate = ViewFieldProvider(
        label = birthDateText,
        wrongText = birthDateWrongText,
        isDate = true
    )

    val gender = ViewFieldProvider(label = genderText)
    val fields = listOf(email, avatarLink, name, birthDate)

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
                getProfile(onUnauthorized)
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

        model.avatarLink = avatarLink.value.value
        model.gender = GenderProviderService.parseToInt(gender.value.value)
        model.name = name.value.value
        model.email = email.value.value
        model.birthDate = DateProviderService.format(birthDate.value.value)

        return model
    }

    private fun parseModel(profile: ProfileModel) {
        profileModel.value = profile

        email.initValue(profile.email)
        name.initValue(profile.name)
        avatarLink.initValue(profile.avatarLink ?: "")

        if (profile.birthDate != null)
            birthDate.initValue(DateProviderService.parseTimestamp(profile.birthDate!!))
        if (profile.gender != null)
            gender.initValue(GenderProviderService.parse(profile.gender!!))

        checkCorrect()
    }

    private fun checkCorrect(): Boolean {
        val isEmailCorrect = checkEmail(email.value.value)
        val isBirthCorrect = checkDate(birthDate.value.value)
        email.isCorrect.value = isEmailCorrect
        birthDate.isCorrect.value = isBirthCorrect

        if (isEmailCorrect && isBirthCorrect)
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
        return DateProviderService.isValidBirth(date)
    }
}