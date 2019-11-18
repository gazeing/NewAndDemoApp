package com.willow.newreactdemoapp

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.Transformations
import io.reactivex.Flowable

fun <T> LiveData<T>.toFlowable(owner: LifecycleOwner): Flowable<T> =
    Flowable.fromPublisher(LiveDataReactiveStreams.toPublisher(owner, this))

fun <T> Flowable<T>.toLiveData(): LiveData<T> = LiveDataReactiveStreams.fromPublisher(this)

fun <T, R> LiveData<T>.switchMap(f: (T) -> LiveData<R>): LiveData<R> = Transformations.switchMap(this, f)