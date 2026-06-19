package com.mayur.core.network.connectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class NetworkMonitor(private val context: Context) {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _isNetworkAvailable = MutableStateFlow(isNetworkAvailable(context))
    val isNetworkAvailable: StateFlow<Boolean> = _isNetworkAvailable.asStateFlow()

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            _isNetworkAvailable.value = true
        }
        override fun onLost(network: Network) {
            _isNetworkAvailable.value = false
        }
        override fun onUnavailable() {
            _isNetworkAvailable.value = false
        }
    }

    init {
        registerCallback()
    }

    private fun registerCallback(){
        // Register the network callback
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    // Don't forget to unregister the callback when no longer needed (e.g., in onCleared for ViewModel, or onDestroy for Service)
    fun unregisterCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    // Reuse your existing isNetworkAvailable function
    private fun isNetworkAvailable(context: Context): Boolean {
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}