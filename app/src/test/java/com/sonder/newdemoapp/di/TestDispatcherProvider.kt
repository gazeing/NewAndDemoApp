package com.sonder.newdemoapp.di

import kotlinx.coroutines.Dispatchers

class TestDispatcherProvider : DispatcherProvider {
    override fun io() = Dispatchers.Unconfined

    override fun ui() = Dispatchers.Unconfined

    override fun computation() = Dispatchers.Unconfined
}