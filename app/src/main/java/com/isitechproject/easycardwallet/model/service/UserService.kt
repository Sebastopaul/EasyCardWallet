package com.isitechproject.easycardwallet.model.service

import com.isitechproject.easycardwallet.model.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val currentUser: Flow<User?>
    val currentUserId: String

    suspend fun create(user: User, password: String)
    suspend fun getOneByEmail(email: String): User
    suspend fun getOneById(id: String): User
    suspend fun getAllInIdList(ids: Iterable<String>): List<User>
    suspend fun updateAuthUser(user: User)
    suspend fun deleteAuthUser()
}