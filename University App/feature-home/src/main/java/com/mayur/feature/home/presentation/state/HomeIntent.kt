package com.mayur.feature.home.presentation.state

import com.mayur.feature.home.domain.model.University

sealed interface HomeIntent {

    data object Load : HomeIntent

    data object Refresh : HomeIntent

    data class ItemClicked(
        val university: University
    ) : HomeIntent

    data class OpenDetail(
        val university: University
    ) : HomeIntent

    data object Retry : HomeIntent
}