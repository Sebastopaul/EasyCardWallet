package com.isitechproject.easycardwallet.model

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val profilePictureId: Picture = Picture(type = USER_PROFILE_PICTURE),
    val loyaltyCards: MutableList<LoyaltyCard> = mutableListOf(),
)