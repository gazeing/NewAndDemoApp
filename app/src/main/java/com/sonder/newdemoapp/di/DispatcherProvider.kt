package com.sonder.newdemoapp.di

import io.reactivex.Scheduler
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Rx Scheduler Provider
 */
interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}