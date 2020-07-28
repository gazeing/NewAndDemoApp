package com.sonder.newdemoapp.di

import kotlinx.coroutines.Dispatchers

/**
 * Application providers
 */
class ApplicationDispatcherProvider : DispatcherProvider {
    override fun io() = Dispatchers.IO

    override fun ui() = Dispatchers.Main

    override fun computation() = Dispatchers.Default
}