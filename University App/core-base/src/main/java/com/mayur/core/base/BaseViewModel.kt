package com.mayur.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayur.core.common.dispatcher.DispatcherProvider
import com.mayur.core.common.result.NetworkResult
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel(
    private val dispatcherProvider: DispatcherProvider
)  : ViewModel() {

    private val _uiEvent =
        MutableSharedFlow<UiEvent>()

    val uiEvent =
        _uiEvent.asSharedFlow()


    private val coroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            onError(
                throwable.message ?: "Something went wrong"
            )
            onLoading(false)
        }

    /**
     * Flow observer (no loading)
     * Flow observer
     */
    protected fun launchObserver(
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(
            coroutineExceptionHandler
        ) {
            try {
                block()
            } catch (e: Exception) {
                onError(
                    e.message ?: "Unknown error"
                )
            }
        }
    }

    /**
     * For DB / local / generic tasks
     */
    protected fun <T> launchTask(
        showLoading: Boolean = false,
        task: suspend () -> T,
        onSuccess: (T) -> Unit
    ) {
        viewModelScope.launch(
            coroutineExceptionHandler
        ) {
            if (showLoading) {
                onLoading(true)
            }
            try {
                val result = withContext(dispatcherProvider.io) {
                    task()
                }
                onSuccess(result)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            } finally {
                if (showLoading) {
                    onLoading(false)
                }
            }
        }
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
                val result = withContext(dispatcherProvider.io) {
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
                  //  NetworkResult.Loading -> Unit
                }
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                onError(
                    e.message ?: "Unknown error"
                )
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

    protected fun sendEvent(
        event: UiEvent
    ) {
        viewModelScope.launch {
            _uiEvent.emit(event)
        }
    }


    protected fun showToast(
        message: String
    ) {
        sendEvent(
            UiEvent.ShowToast(message)
        )
    }

    protected fun showSnackbar(
        message: String
    ) {
        sendEvent(
            UiEvent.ShowSnackbar(message)
        )
    }

    protected fun navigate(
        route: String
    ) {
        sendEvent(
            UiEvent.Navigate(route)
        )
    }

    protected fun navigateBack() {
        sendEvent(
            UiEvent.NavigateBack
        )
    }


}