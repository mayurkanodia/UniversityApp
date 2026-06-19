package com.mayur.sync

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.mayur.sync.workers.UniversitySyncWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton



@Singleton
class SyncManager @Inject constructor(
    private val workManager: WorkManager
) {

    companion object {
        private const val PRODUCT_SYNC_ONE_TIME =
            "product_sync_one_time"

        private const val PRODUCT_SYNC_PERIODIC =
            "product_sync_periodic"
    }

    fun startOneTimeSync() {
        val request =
            OneTimeWorkRequestBuilder<UniversitySyncWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(
                            NetworkType.CONNECTED
                        )
                        .build()
                )
                .build()

        workManager.enqueueUniqueWork(
            PRODUCT_SYNC_ONE_TIME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }

    fun startPeriodicSync() {
        val request =
            PeriodicWorkRequestBuilder<UniversitySyncWorker>(
                15,
                TimeUnit.MINUTES
            )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(
                            NetworkType.CONNECTED
                        )
                        .build()
                )
                .build()

        workManager.enqueueUniquePeriodicWork(
            PRODUCT_SYNC_PERIODIC,
            ExistingPeriodicWorkPolicy.KEEP,
            request
        )
    }
}