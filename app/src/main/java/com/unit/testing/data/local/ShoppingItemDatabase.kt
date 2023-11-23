package com.unit.testing.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unit.testing.data.local.entity.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version = 1
)
abstract class ShoppingItemDatabase : RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao
}