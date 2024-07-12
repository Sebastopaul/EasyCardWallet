package com.isitechproject.easycardwallet.model

data class LoyaltyCard(
    val company: String = "",
    val code: String = "",
    val picture: String = "",
    val uid: String = "",
) : Model()