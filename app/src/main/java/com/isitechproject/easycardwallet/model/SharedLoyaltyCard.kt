package com.isitechproject.easycardwallet.model

data class SharedLoyaltyCard(
    val loyaltyCardId: String = "",
    val sharedUid: String = "",
    val uid: String = "",
) : Model()