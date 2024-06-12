package com.isitechproject.easycardwallet.model

data class Group(
    val id: String = "",
    val name: String = "",
    val members: MutableList<GroupMember> = mutableListOf(),
)