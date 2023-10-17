package com.unit.testing.data.repository

import com.unit.testing.data.local.UserDao
import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.domain.repository.UserRepository
import com.unit.testing.utils.Resource
import com.unit.testing.utils.SimpleResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun isUserExist(userId: String) =
        flow { emit(userDao.isUserExist(userId)) }.flowOn(Dispatchers.Default)

    override suspend fun isUserExist(email: String, password: String) =
        flow {
            emit(Resource.Loading())
            if (userDao.isUserExist(email, password)) {
                emit(Resource.Success(true))
            } else {
                emit(Resource.Error("User Not Exist"))
            }
        }

    override suspend fun getUserByEmailAndPassword(
        email: String,
        password: String
    ) =
        flow {
            emit(Resource.Loading())
            if (userDao.isUserExist(email, password)) {
                emit(Resource.Success(userDao.getUserByEmailAndPassword(email, password)))
            } else {
                emit(Resource.Error("User Not Exist"))
            }
        }

    override suspend fun insertUser(model: UserEntity): Flow<Resource<List<Long>>> =
        flow {
            emit(Resource.Loading())
            if (userDao.isUserNameExist(model.userName)) {
                emit(Resource.Error("UserName Exist"))
            } else if (userDao.isUserEmailExist(model.email)) {
                emit(Resource.Error("Email Exist"))
            } else {
                emit(Resource.Success(userDao.insertUser(model)))
            }
        }

    override suspend fun getUserById(userId: String) =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(userDao.getUserById(userId)))
            } catch (exp: Exception) {
                emit(Resource.Error(exp.toString()))
            }
        }
//        flow { emit(userDao.getUserById(userId)) }.flowOn(Dispatchers.Default)

    override suspend fun getAllUsers() =
        flow {
            emit(Resource.Loading())
            try {
                emit(Resource.Success(userDao.getAllUsers()))
            } catch (exp: Exception) {
                emit(Resource.Error(exp.toString()))
            }
        }
//        flow { emit(userDao.getAllUsers()) }.flowOn(Dispatchers.Default)

    override suspend fun deleteUser(model: UserEntity) =
        flow { emit(userDao.deleteUser(model)) }.flowOn(Dispatchers.Default)

    override suspend fun deleteAllUsers() =
        flow { emit(userDao.deleteAllUsers()) }.flowOn(Dispatchers.Default)

    override suspend fun register(model: UserEntity) =
        flow {
            emit(Resource.Loading())
            if (userDao.isUserNameExist(model.userName)) {
                emit(Resource.Error("UserName Exist"))
            } else if (userDao.isUserEmailExist(model.email)) {
                emit(Resource.Error("Email Exist"))
            } else {
                emit(Resource.Success(userDao.insertUser(model)))
            }
        }

    suspend fun register123(model: UserEntity): SimpleResource {
        return if (userDao.isUserNameExist(model.userName) && userDao.isUserEmailExist(model.email)) {
            Resource.Error("Error")
        } else {
            if (userDao.insertUser(model).isNotEmpty())
                Resource.Success(Unit)
            else
                Resource.Error("Unknown Error")

        }
    }


}