package com.unit.testing.domain.repository

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.utils.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun insertUser(model: UserEntity): Flow<Resource<List<Long>>>//SimpleResource//Flow<List<Long>>
    suspend fun register(model: UserEntity): Flow<Resource<List<Long>>>

    suspend fun getUserById(userId: String): Flow<Resource<UserEntity>>

    suspend fun getAllUsers(): Flow<Resource<MutableList<UserEntity>>>

    suspend fun deleteAllUsers(): Flow<Int>

    suspend fun deleteUser(model: UserEntity): Flow<Int>

    suspend fun isUserExist(userId: String): Flow<Boolean>

    suspend fun isUserExist(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun getUserByEmailAndPassword(email: String, password: String): Flow<Resource<UserEntity>>
}