package com.isitechproject.easycardwallet.model

data class BusinessCard(
    val companyName: String = "",
    val address: String = "",
    val zip: String = "",
    val city: String = "",
    val contactFirstname: String = "",
    val contactLastname: String = "",
    val contactPhone: String = "",
    val contactMobile: String = "",
    val contactEmail: String = "",
    override val name: String = "",
    override val picture: String = "",
    override val uid: String = ""
) : AbstractCard(name, picture, uid) {
    companion object {
        const val COMPANY_NAME_FIELD = "companyName"
        const val ADDRESS_FIELD = "address"
        const val ZIP_FIELD = "zip"
        const val CITY_FIELD = "city"
        const val CONTACT_FIRSTNAME_FIELD = "contactFirstname"
        const val CONTACT_LASTNAME_FIELD = "contactLastname"
        const val CONTACT_PHONE_FIELD = "contactPhone"
        const val CONTACT_MOBILE_FIELD = "contactMobile"
        const val CONTACT_EMAIL_FIELD = "contactEmail"
        const val NAME_FIELD = "name"
        const val PICTURE_FIELD = "picture"
    }
}