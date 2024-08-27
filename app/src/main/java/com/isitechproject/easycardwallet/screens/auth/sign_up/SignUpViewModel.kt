package com.isitechproject.easycardwallet.screens.auth.sign_up

import android.graphics.Bitmap
import com.isitechproject.easycardwallet.LOYALTY_CARDS_LIST_SCREEN
import com.isitechproject.easycardwallet.SIGN_UP_SCREEN
import com.isitechproject.easycardwallet.model.User
import com.isitechproject.easycardwallet.model.service.AccountService
import com.isitechproject.easycardwallet.model.service.UserService
import com.isitechproject.easycardwallet.screens.EasyCardWalletAppViewModel
import com.isitechproject.easycardwallet.utils.ImageConverterBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    accountService: AccountService,
    private val userService: UserService
) : EasyCardWalletAppViewModel(accountService) {
    val email = MutableStateFlow("")
    val firstname = MutableStateFlow("")
    val lastname = MutableStateFlow("")
    val profilePicture = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")

    fun updateEmail(newEmail: String) {
        email.value = newEmail
    }

    fun updateFirstname(newFirstname: String) {
        firstname.value = newFirstname
    }

    fun updateLastname(newLastname: String) {
        lastname.value = newLastname
    }

    fun updateProfilePicture(newProfilePicture: Bitmap) {
        profilePicture.value = ImageConverterBase64.to(newProfilePicture) ?: ""
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (password.value != confirmPassword.value) {
                throw Exception("Passwords do not match")
            }

            userService.create(User(
                email = email.value,
                firstname = firstname.value,
                lastname = lastname.value,
                profilePicture = profilePicture.value,
            ), password.value)
            openAndPopUp(LOYALTY_CARDS_LIST_SCREEN, SIGN_UP_SCREEN)
        }
    }
}