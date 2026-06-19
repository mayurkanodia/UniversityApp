package com.mayur.testapplicatiom.test.prasentation.viewmodel

import com.mayur.testapplicatiom.test.domain.usecase.GetUserUseCase
import com.mayur.testapplicatiom.test.prasentation.states.UserUiState
import com.mayur.testapplicatiom.utils.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (private val getUserUseCase : GetUserUseCase) : BaseViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState.asStateFlow()

    init {
        getData()
    }


    private fun getData() {
        launchNetworkCall(
            apiCall = {
                getUserUseCase()
            },
            onSuccess = { products ->
                _uiState.update {
                    it.copy(
                        products = products
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

}