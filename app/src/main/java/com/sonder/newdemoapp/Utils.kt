package com.sonder.newdemoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations


fun <T, R> LiveData<T>.switchMap(f: (T) -> LiveData<R>): LiveData<R> = Transformations.switchMap(this, f)