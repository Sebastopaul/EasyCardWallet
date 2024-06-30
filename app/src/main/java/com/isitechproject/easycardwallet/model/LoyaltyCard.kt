package com.isitechproject.easycardwallet.model

data class LoyaltyCard(
    val id: String = "",
    val company: String = "",
    val code: String = "",
    val picture: Picture = Picture(type = LOYALTY_CARD_PICTURE),
)