
package com.mayur.core.network.util

import com.mayur.core.common.result.NetworkResult
import kotlinx.coroutines.delay
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException


// Generic version for non-Response APIs
suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): NetworkResult<T> {
    return try {
        val response = apiCall()
        NetworkResult.Success(response)
    } catch (e: HttpException) {
        NetworkResult.Failure(e.code(), e.message() ?: "HTTP error") // HTTP errors
    }catch (e: SocketTimeoutException) {
        NetworkResult.Error(e) // Socket Timeout issues
    } catch (e: IOException) {
        NetworkResult.Error(e) // Network issues
    } catch (e: Exception) {
        NetworkResult.Error(e) // Unexpected exceptions
    }
}

// Version for APIs that return Response<T>
suspend fun <T> safeApiCallWithNetworkResponse(apiCall: suspend () -> Response<T>): NetworkResult<T> {
    return try {
        val response = apiCall()
        if (response.isSuccessful && response.body() != null) {
            NetworkResult.Success(response.body()!!)
        } else {
            NetworkResult.Failure(response.code(), "Error Code: ${response.code()}")
        }
    } catch (e: HttpException) {
        NetworkResult.Error(e) // HTTP errors
    } catch (e: IOException) {
        NetworkResult.Error(e) // Network issues
    } catch (e: Exception) {
        NetworkResult.Error(e) // Unexpected exceptions
    }
}


suspend fun <T> retryIO(times: Int = 3,
                        delayTime: Long = 1000,
                        block: suspend () -> T): T {
    repeat(times - 1) {
        try {
            return block()
        } catch (e: IOException) {
            delay(delayTime)
        }
    }
    return block() // last attempt
}




suspend fun <T> retryIO1(
    times: Int = 3,
    initialDelay: Long = 500,
    maxDelay: Long = 3000,
    factor: Double = 2.0,
    block: suspend () -> T
): T {

    var currentDelay = initialDelay

    repeat(times - 1) { attempt ->
        try {
            return block()
        } catch (e: IOException) {
            // Optional log
            println("Retry attempt ${attempt + 1}")

            delay(currentDelay)
            currentDelay = (currentDelay * factor)
                .toLong()
                .coerceAtMost(maxDelay)
        }
    }

    return block() // last attempt
}

