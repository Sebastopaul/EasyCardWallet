package com.isitechproject.easycardwallet.model

data class GroupMember(
    val id: String = "",
    val userId: String = "",
    val isModerator: Boolean = false,
    val sharedLoyaltyCards: MutableList<SharedLoyaltyCard> = mutableListOf(),
)
