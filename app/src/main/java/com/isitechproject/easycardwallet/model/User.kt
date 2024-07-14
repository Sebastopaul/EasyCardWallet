package com.isitechproject.easycardwallet.model

data class User(
    val uid: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val profilePicture: String = "",
) : Model()