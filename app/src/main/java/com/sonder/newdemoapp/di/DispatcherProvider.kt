package com.sonder.newdemoapp.di

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Coroutine Scheduler Provider
 */
interface DispatcherProvider {
    fun io(): CoroutineDispatcher
    fun ui(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}