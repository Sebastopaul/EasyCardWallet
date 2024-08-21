package com.isitechproject.easycardwallet.model

abstract class AbstractSharedCard(
    open val sharedCardId: String,
    open val sharedUid: String,
    open val uid: String,
): Model()