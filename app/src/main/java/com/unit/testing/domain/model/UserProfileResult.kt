package com.unit.testing.domain.model

import com.unit.testing.data.local.entity.UserEntity
import com.unit.testing.utils.Resource
import kotlinx.coroutines.flow.Flow

data class UserProfileResult(
    val result: Flow<Resource<UserEntity>>? = null
)
