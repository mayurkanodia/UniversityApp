package com.mayur.feature.home.presentation.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import com.mayur.core.base.BaseViewModel
import com.mayur.core.base.UiEvent
import com.mayur.core.base.navigation.AppRoutes
import com.mayur.core.common.dispatcher.DispatcherProvider
import com.mayur.feature.home.domain.usecase.GetUniversityUseCase
import com.mayur.feature.home.domain.usecase.RefreshUniversityUseCase
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

    init {
        observeUniversities()
        refresh()
    }

    private fun observeUniversities() {
        launchObserver {
            getUniversityUseCase()
                .distinctUntilChanged()
                .collect { universities ->
                _uiState.update {
                    it.copy(
                        universities = universities
                    )
                }
            }
        }
    }

     fun refresh() {
        if (uiState.value.isLoading) return
        launchNetworkCall(
            apiCall = {
                refreshUniversityUseCase()
            },
            onSuccess = {
               /* showSnackbar(
                    "Data synced successfully"
                )*/
                _uiState.update {
                    it.copy(
                        error = null
                    )
                }
            }
        )
    }

    public override fun onLoading(
        isLoading: Boolean
    ) {
        _uiState.update {
            it.copy(
                isLoading = isLoading
            )
        }

    }

    public override fun onError(
        message: String
    ) {
        _uiState.update {
            it.copy(
                error = message
            )
        }
    }

    fun onItemClick(
        name: String,
        country: String
    ) {
        sendEvent(
            UiEvent.ShowProductDialog(
                name = name,
                country = country
            )
        )
    }

    fun openProductDetail(universityName: String
    ) {
        navigate(
            "${AppRoutes.DETAIL}/$universityName"
        )
    }
}