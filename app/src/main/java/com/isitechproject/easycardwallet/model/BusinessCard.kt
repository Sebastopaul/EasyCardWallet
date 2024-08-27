package com.isitechproject.easycardwallet.model

data class BusinessCard(
    val companyName: String = "",
    val contactFirstname: String = "",
    val contactLastname: String = "",
    val contactPhone: String = "",
    val contactMobile: String = "",
    val contactEmail: String = "",
    override val name: String = "",
    override val picture: String = "",
    override val uid: String = "",
) : AbstractCard(name, picture, uid)