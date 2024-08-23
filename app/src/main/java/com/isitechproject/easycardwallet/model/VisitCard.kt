package com.isitechproject.easycardwallet.model

data class VisitCard(
    override val name: String = "",
    override val picture: String = "",
    override val uid: String = "",
) : AbstractCard(name, picture, uid)