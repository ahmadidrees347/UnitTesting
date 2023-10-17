package com.unit.testing.domain.usecase

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserLogin @Inject constructor(private val repository: UserRepository) {

    suspend fun getUserByEmailAndPassword(email: String, password: String): Flow<Boolean> {
        return repository.isUserExist(email, password)
    }

    suspend fun getUserById(userId: String): Flow<UserEntity> {
        if (userId.isBlank()) {
            return flow { }
        }
        return repository.getUserById(userId)
    }
}