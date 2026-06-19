package com.mayur

import android.app.Application
import androidx.work.Configuration
import dagger.hilt.android.HiltAndroidApp
import androidx.hilt.work.HiltWorkerFactory
import com.mayur.sync.SyncManager
import javax.inject.Inject

@HiltAndroidApp
class UniversityApp : Application(),
    Configuration.Provider {

    @Inject
    lateinit var workerFactory:
            HiltWorkerFactory

    @Inject
    lateinit var syncManager:
            SyncManager

    override val workManagerConfiguration:
            Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        syncManager.startPeriodicSync()
    }
}