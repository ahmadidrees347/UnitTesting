package com.unit.testing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unit.testing.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}