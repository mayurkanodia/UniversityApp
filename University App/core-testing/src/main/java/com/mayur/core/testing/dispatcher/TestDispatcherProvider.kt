package com.mayur.core.testing.dispatcher

import com.mayur.core.common.dispatcher.DispatcherProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@OptIn(ExperimentalCoroutinesApi::class)
class TestDispatcherProvider :
    DispatcherProvider {
    override val main =
        UnconfinedTestDispatcher()

    override val io =
        UnconfinedTestDispatcher()

    override val default =
        UnconfinedTestDispatcher()

    override val unconfined =
        UnconfinedTestDispatcher()
}