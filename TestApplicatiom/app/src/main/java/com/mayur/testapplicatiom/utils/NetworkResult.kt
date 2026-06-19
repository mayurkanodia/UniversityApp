package com.mayur.testapplicatiom.utils


sealed class NetworkResult<out T> {
    //data object Loading : NetworkResult<Nothing>()
    data class Failure(
        val code: Int?,
        val message: String
    ) : NetworkResult<Nothing>()

    data class Success<out T>(
        val data: T
    ) : NetworkResult<T>()

    data class Error(
        val exception: Throwable
    ) : NetworkResult<Nothing>()
}