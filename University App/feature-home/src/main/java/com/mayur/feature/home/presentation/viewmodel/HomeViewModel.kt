package com.mayur.feature.home.presentation.viewmodel

import com.mayur.core.base.BaseViewModel
import com.mayur.core.base.UiEvent
import com.mayur.core.base.navigation.AppRoutes
import com.mayur.core.common.dispatcher.DispatcherProvider
import com.mayur.feature.home.domain.usecase.GetUniversityUseCase
import com.mayur.feature.home.domain.usecase.RefreshUniversityUseCase
import com.mayur.feature.home.presentation.state.HomeIntent
import com.mayur.feature.home.presentation.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getUniversityUseCase: GetUniversityUseCase,
    private val refreshUniversityUseCase: RefreshUniversityUseCase,
    dispatcherProvider: DispatcherProvider,
) : BaseViewModel(dispatcherProvider) {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private fun observeUniversities() {
        launchObserver {
            getUniversityUseCase()
                .distinctUntilChanged()
                .collect { universities ->
                    reduce {
                        copy(
                            universities = universities
                        )
                    }
                }
        }
    }

    private fun refresh() {
        if (uiState.value.isLoading) return
        launchNetworkCall(
            apiCall = {
                refreshUniversityUseCase()
            },
            onSuccess = {
                reduce {
                    copy(
                        error = null
                    )
                }
            }
        )
    }

    public override fun onLoading(
        isLoading: Boolean
    ) {
        reduce {
            copy(
                isLoading = isLoading
            )
        }
    }

    public override fun onError(
        message: String
    ) {
        reduce {
            copy(
                error = message
            )
        }
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.Load -> {
                observeUniversities()
                refresh()
            }

            HomeIntent.Refresh -> refresh()

            is HomeIntent.ItemClicked -> sendEvent(
                UiEvent.ShowProductDialog(
                    name = intent.university.name,
                    country = intent.university.country
                )
            )

            is HomeIntent.OpenDetail -> navigate(
                "${AppRoutes.DETAIL}/${intent.university.name}"
            )

            HomeIntent.Retry -> refresh()
        }
    }

    private fun reduce(
        reducer: HomeUiState.() -> HomeUiState
    ) {
        _uiState.update {
            it.reducer()
        }
    }
}