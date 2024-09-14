package com.isitechproject.businesscardscanner

import com.isitechproject.easycardwallet.model.BusinessCard

const val CREATE_BUSINESS_CARD_SCREEN = "CreateBusinessCardScreen"
const val EASY_CARD_WALLET_MAIN_SCREEN = "EasyCardWalletMainScreen"

const val BUSINESS_CARD_DEFAULT = ""
const val BUSINESS_CARD_ARG = "?${BusinessCard.COMPANY_NAME_FIELD}={${BusinessCard.COMPANY_NAME_FIELD}}" +
        "&${BusinessCard.ADDRESS_FIELD}={${BusinessCard.ADDRESS_FIELD}}" +
        "&${BusinessCard.ZIP_FIELD}={${BusinessCard.ZIP_FIELD}}" +
        "&${BusinessCard.CITY_FIELD}={${BusinessCard.CITY_FIELD}}" +
        "&${BusinessCard.CONTACT_FIRSTNAME_FIELD}={${BusinessCard.CONTACT_FIRSTNAME_FIELD}}" +
        "&${BusinessCard.CONTACT_LASTNAME_FIELD}={${BusinessCard.CONTACT_LASTNAME_FIELD}}" +
        "&${BusinessCard.CONTACT_PHONE_FIELD}={${BusinessCard.CONTACT_PHONE_FIELD}}" +
        "&${BusinessCard.CONTACT_MOBILE_FIELD}={${BusinessCard.CONTACT_MOBILE_FIELD}}" +
        "&${BusinessCard.CONTACT_EMAIL_FIELD}={${BusinessCard.CONTACT_EMAIL_FIELD}}"
