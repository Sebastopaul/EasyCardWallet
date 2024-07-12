package com.isitechproject.easycardwallet.model

import kotlinx.coroutines.flow.Flow

data class User(
    val uid: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val profilePicture: String = "",
) : Model()