package com.unit.testing.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.unit.testing.data.local.entity.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(vararg model: UserEntity): List<Long>

    @Delete
    suspend fun deleteUser(vararg model: UserEntity): Int

    @Query("SELECT * FROM userEntity")
    suspend fun getAllUsers(): MutableList<UserEntity>

    @Query("DELETE FROM userEntity")
    suspend fun deleteAllUsers(): Int

    @Query("SELECT * FROM userEntity WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity

    @Query("SELECT EXISTS(SELECT * FROM userEntity WHERE id = :userId)")
    suspend fun isUserExist(userId: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM userEntity WHERE email = :email AND password = :password)")
    suspend fun isUserExist(email: String, password: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM userEntity WHERE userName = :userName)")
    suspend fun isUserNameExist(userName: String): Boolean

    @Query("SELECT EXISTS(SELECT * FROM userEntity WHERE email = :email)")
    suspend fun isUserEmailExist(email: String): Boolean
}