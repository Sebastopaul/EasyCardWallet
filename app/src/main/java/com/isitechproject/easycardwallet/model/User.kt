package com.isitechproject.easycardwallet.model

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val profilePicture: Picture = Picture(type = USER_PROFILE_PICTURE),
    val loyaltyCards: MutableList<LoyaltyCard> = mutableListOf(),
)