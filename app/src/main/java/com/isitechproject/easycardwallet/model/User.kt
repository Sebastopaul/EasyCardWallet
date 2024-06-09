package com.isitechproject.easycardwallet.model

import kotlinx.coroutines.flow.Flow

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val profilePicture: Picture = Picture(type = USER_PROFILE_PICTURE),
)