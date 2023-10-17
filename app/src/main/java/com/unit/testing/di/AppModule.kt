package com.unit.testing.di

import android.content.Context
import androidx.room.Room
import com.unit.testing.data.local.LocalDatabase
import com.unit.testing.data.repository.UserRepositoryImpl
import com.unit.testing.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWordInfoRepository(
        db: LocalDatabase,
    ): UserRepository {
        return UserRepositoryImpl(db.userDao)
    }

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            "users.db"
        ).build()
    }
}