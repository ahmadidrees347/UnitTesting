package com.unit.testing.di

import android.content.Context
import com.unit.testing.R
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.unit.testing.data.local.LocalDatabase
import com.unit.testing.data.local.ShoppingDao
import com.unit.testing.data.local.ShoppingItemDatabase
import com.unit.testing.data.remote.PixabayAPI
import com.unit.testing.data.repository.ShoppingRepositoryImpl
import com.unit.testing.data.repository.UserRepositoryImpl
import com.unit.testing.domain.repository.ShoppingRepository
import com.unit.testing.domain.repository.UserRepository
import com.unit.testing.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_image)
            .error(R.drawable.ic_image)
    )

    @Provides
    @Singleton
    fun provideBeerDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.databaseBuilder(
            context,
            LocalDatabase::class.java,
            Constants.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ShoppingItemDatabase::class.java, Constants.DB_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: ShoppingItemDatabase
    ) = database.shoppingDao()

    @Singleton
    @Provides
    fun provideDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = ShoppingRepositoryImpl(dao, api) as ShoppingRepository

    @Singleton
    @Provides
    fun providePixabayApi(): PixabayAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }
}