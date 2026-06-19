package com.mayur.testapplicatiom.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException


abstract class BaseViewModel(
)  : ViewModel() {

    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            onError(
                throwable.message ?: "Something went wrong"
            )
            onLoading(false)
        }


    /**
     * API call with loading + error handling
     * For API calls returning NetworkResult
     */
    protected fun <T> launchNetworkCall(
        showLoading: Boolean = true,
        apiCall: suspend () -> NetworkResult<T>,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch(
            coroutineExceptionHandler
        ) {
            if (showLoading) {
                onLoading(true)
            }
            try {
                val result = withContext(Dispatchers.IO) {
                    apiCall()
                }
                when (result) {
                    is NetworkResult.Success -> {
                        onSuccess(result.data)
                    }
                    is NetworkResult.Failure -> {
                        onError(result.message)
                    }
                    is NetworkResult.Error -> {
                        onError(result.exception.message ?: "Unknown error")
                    }
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            } finally {
                if (showLoading) {
                    onLoading(false)
                }
            }
        }
    }


    protected abstract fun onLoading(
        isLoading: Boolean
    )

    protected abstract fun onError(
        message: String
    )

}