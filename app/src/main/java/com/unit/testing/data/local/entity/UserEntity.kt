package com.unit.testing.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "userEntity")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val email: String,
    val password: String
): Parcelable {
    override fun toString(): String {
        return "UserEntity(id=$id, userName='$userName', email='$email', password='$password')"
    }
}