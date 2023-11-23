package com.unit.testing.data.repository

import com.unit.testing.data.local.ShoppingDao
import com.unit.testing.data.local.entity.ShoppingItem
import com.unit.testing.data.remote.PixabayAPI
import com.unit.testing.domain.repository.ShoppingRepository
import retrofit2.Response
import javax.inject.Inject
import androidx.lifecycle.LiveData
import com.unit.testing.data.remote.responses.ImageResponse
import com.unit.testing.utils.Resource

class ShoppingRepositoryImpl @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
) : ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.Success(it)
                } ?: Resource.Error("An unknown error occured", null)
            } else {
                Resource.Error("An unknown error occured", null)
            }
        } catch(e: Exception) {
            Resource.Error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}