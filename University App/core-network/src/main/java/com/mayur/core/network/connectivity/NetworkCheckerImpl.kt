package com.mayur.core.network.connectivity

import android.Manifest
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.annotation.RequiresPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkCheckerImpl @Inject constructor(
    @ApplicationContext
    private val context: Context
) : NetworkChecker {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    override fun isConnected(): Boolean {

        val manager =
            context.getSystemService(
                ConnectivityManager::class.java
            )

        val network =
            manager.activeNetwork ?: return false

        val capabilities =
            manager.getNetworkCapabilities(network)
                ?: return false

        return capabilities.hasCapability(
            NetworkCapabilities.NET_CAPABILITY_INTERNET
        )
    }
}