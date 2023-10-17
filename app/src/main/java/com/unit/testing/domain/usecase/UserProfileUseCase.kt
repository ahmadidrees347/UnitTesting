package com.unit.testing.domain.usecase

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import com.unit.testing.domain.model.AllUsersResult
import com.unit.testing.domain.model.UserProfileResult
import javax.inject.Inject

class UserProfileUseCase @Inject constructor(private val repository: UserRepository) {

    suspend fun getAllUsers(): AllUsersResult {
        val result = repository.getAllUsers()
        return AllUsersResult(result = result)
    }

    suspend operator fun invoke(userId: String): UserProfileResult {
        val result = repository.getUserById(userId)
        return UserProfileResult(result = result)
    }
}