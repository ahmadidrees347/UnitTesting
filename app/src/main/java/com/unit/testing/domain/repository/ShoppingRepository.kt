package com.unit.testing.domain.repository

import androidx.lifecycle.LiveData
import com.unit.testing.data.local.entity.ShoppingItem
import com.unit.testing.data.remote.responses.ImageResponse
import com.unit.testing.utils.Resource


interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}