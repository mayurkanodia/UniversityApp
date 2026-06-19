
package com.mayur.feature.home.presentation.state

import com.mayur.feature.home.domain.model.University

data class HomeUiState(
    val universities: List<University> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)