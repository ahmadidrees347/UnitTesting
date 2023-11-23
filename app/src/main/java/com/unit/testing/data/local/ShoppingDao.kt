package com.unit.testing.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.unit.testing.data.local.entity.ShoppingItem

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    @Delete
    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    @Query("SELECT * FROM shopping_items")
    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    @Query("SELECT SUM(price * amount) FROM shopping_items")
    fun observeTotalPrice(): LiveData<Float>
}