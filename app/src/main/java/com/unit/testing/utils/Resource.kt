package com.unit.testing.utils

typealias SimpleResource = Resource<Unit>

sealed class Resource<T>(
    val status: Status? = null,
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data = data)
    class Loading<T>(data: T? = null) : Resource<T>(Status.LOADING, data = data)
    class Error<T>(message: String, data: T? = null) :
        Resource<T>(Status.ERROR, data = data, message)
}

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}