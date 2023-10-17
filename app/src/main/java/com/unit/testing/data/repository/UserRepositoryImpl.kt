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
import retrofit2.HttpException
import java.io.IOException

class UserRepositoryImpl(
    private val userDao: UserDao
) : UserRepository {

    override suspend fun isUserExist(userId: String) =
        flow { emit(userDao.isUserExist(userId)) }.flowOn(Dispatchers.Default)

    override suspend fun isUserExist(email: String, password: String) =
        flow { emit(userDao.isUserExist(email, password)) }.flowOn(Dispatchers.Default)

    suspend fun insertUserPrev(models: UserEntity) =
        flow { emit(userDao.insertUser(models)) }.flowOn(Dispatchers.Default)

    override suspend fun insertUser(model: UserEntity): Flow<Resource<List<Long>>> =
        flow {
            try {
                emit(Resource.Loading())
                if (userDao.isUserNameExist(model.userName) && userDao.isUserEmailExist(model.email)) {
                    emit(Resource.Error("Error"))
                }
                emit(Resource.Success(userDao.insertUser(model)))
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }
//    {
//
//        return if (userDao.isUserNameExist(model.userName) && userDao.isUserEmailExist(model.email)) {
//            Resource.Error("Error")
//        } else {
//            if (userDao.insertUser(model).isNotEmpty())
//                Resource.Success(Unit)
//            else
//                Resource.Error("Unknown Error")
//        }
//    }


    override suspend fun getUserById(userId: String) =
        flow { emit(userDao.getUserById(userId)) }.flowOn(Dispatchers.Default)

    override suspend fun getAllUsers() =
        flow { emit(userDao.getAllUsers()) }.flowOn(Dispatchers.Default)

    override suspend fun deleteUser(model: UserEntity) =
        flow { emit(userDao.deleteUser(model)) }.flowOn(Dispatchers.Default)

    override suspend fun deleteAllUsers() =
        flow { emit(userDao.deleteAllUsers()) }.flowOn(Dispatchers.Default)

    override suspend fun register(model: UserEntity): Flow<Resource<List<Long>>> =
        flow {
            try {
                emit(Resource.Loading())
                if (userDao.isUserNameExist(model.userName)) {
                    emit(Resource.Error("UserName Exist"))
                } else if (userDao.isUserEmailExist(model.email)) {
                    emit(Resource.Error("Email Exist"))
                } else {
                    emit(Resource.Success(userDao.insertUser(model)))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    suspend fun registerNew(model: UserEntity): Flow<Resource<List<Long>>> =
        flow {
            try {
                emit(Resource.Loading())
                if (userDao.isUserNameExist(model.userName) && userDao.isUserEmailExist(model.email)) {
                    emit(Resource.Error("Error"))
                } else {
                    emit(Resource.Success(userDao.insertUser(model)))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            }
        }

    suspend fun register332(model: UserEntity): Resource<List<Long>> {
        return if (userDao.isUserNameExist(model.userName) && userDao.isUserEmailExist(model.email)) {
            Resource.Error("Error: UserName already Exist")
        } else if (userDao.isUserEmailExist(model.email)) {
            Resource.Error("Error: Email already Exist")
        } else {
            Resource.Success(userDao.insertUser(model))
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
//            flow { emit(userDao.insertUser(model)) }.flowOn(Dispatchers.Default)

        }
    }


}