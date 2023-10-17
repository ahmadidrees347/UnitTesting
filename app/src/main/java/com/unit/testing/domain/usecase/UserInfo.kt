package com.unit.testing.domain.usecase

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserInfo @Inject constructor (private val repository: UserRepository) {

    suspend fun getAllUsers(): Flow<List<UserEntity>> {
        return repository.getAllUsers()
    }

    suspend fun getUserById(userId: String): Flow<UserEntity> {
        if (userId.isBlank()) {
            return flow { }
        }
        return repository.getUserById(userId)
    }
}