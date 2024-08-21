package com.isitechproject.easycardwallet.model

data class LoyaltyCard(
    override val name: String = "",
    val data: String = "",
    override val picture: String = "",
    override val uid: String = "",
) : AbstractCard(name, picture, uid)