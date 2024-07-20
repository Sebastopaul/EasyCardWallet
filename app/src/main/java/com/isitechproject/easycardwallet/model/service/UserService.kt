package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val currentUser: Flow<User?>
    val currentUserId: String

    suspend fun create(user: User, password: String)
    suspend fun updateAuthUser(user: User)
    suspend fun deleteAuthUser()
}