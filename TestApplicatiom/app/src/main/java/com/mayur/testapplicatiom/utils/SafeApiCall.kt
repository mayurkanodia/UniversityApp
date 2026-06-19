
package com.mayur.testapplicatiom.utils

import retrofit2.HttpException
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

