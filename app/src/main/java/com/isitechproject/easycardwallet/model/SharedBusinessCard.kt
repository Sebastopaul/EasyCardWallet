package com.isitechproject.easycardwallet.model

data class SharedBusinessCard(
    override val sharedCardId: String = "",
    override val sharedUid: String = "",
    override val uid: String = "",
) : AbstractSharedCard(sharedCardId, sharedUid, uid)