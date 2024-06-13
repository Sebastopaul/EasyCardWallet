package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.User
import kotlinx.coroutines.flow.Flow

interface UserDataService {
    val currentUser: Flow<User?>
    val currentUserId: String

    suspend fun create(user: User)
    suspend fun getAuthUser(): User?
    suspend fun updateAuthUser(user: User)
    suspend fun deleteAuthUser()
}