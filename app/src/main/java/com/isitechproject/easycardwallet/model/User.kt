package com.isitechproject.easycardwallet.model

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val profilePictureId: String = "",
    val loyaltyCards: MutableList<LoyaltyCard> = mutableListOf(),
)