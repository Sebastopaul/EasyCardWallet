package com.isitechproject.easycardwallet.model

import kotlinx.coroutines.flow.Flow

data class GroupMember(
    val id: String = "",
    val loyaltyCardsIds: MutableList<String> = mutableListOf(),
    val isModerator: Boolean = false,
    val userId: String = "",
)
