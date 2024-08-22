package com.isitechproject.easycardwallet.model

abstract class AbstractCard(
    open val name: String,
    open val picture: String,
    open val uid: String,
): Model()