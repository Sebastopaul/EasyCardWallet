package com.isitechproject.easycardwallet.model

data class SharedLoyaltyCard(
    override val sharedCardId: String = "",
    override val sharedUid: String = "",
    override val uid: String = "",
) : AbstractSharedCard(sharedCardId, sharedUid, uid)