package com.mayur.core.common.dispatcher


import kotlinx.coroutines.Dispatchers

// To avoid hardcoded dispatchers, improve testability,
// and control coroutine execution in unit tests.
class DefaultDispatcherProvider :
    DispatcherProvider {

    override val main =
        Dispatchers.Main

    override val io =
        Dispatchers.IO

    override val default =
        Dispatchers.Default

    override val unconfined =
        Dispatchers.Unconfined
}