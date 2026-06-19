package com.mayur.sync.workers

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.mayur.core.common.result.NetworkResult
import com.mayur.feature.home.domain.usecase.RefreshUniversityUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class UniversitySyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val refreshUniversityUseCase: RefreshUniversityUseCase
) : CoroutineWorker(
    context,
    params
) {

    override suspend fun doWork(): Result {

        return when (
            refreshUniversityUseCase()
        ) {

            is NetworkResult.Success -> {
                Result.success()
            }

            is NetworkResult.Failure -> {
                Result.retry()
            }

            is NetworkResult.Error -> {
                Result.retry()
            }
        }
    }
}