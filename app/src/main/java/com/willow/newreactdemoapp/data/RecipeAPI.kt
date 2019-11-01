package com.willow.newreactdemoapp.data

import com.willow.newreactdemoapp.model.RecipeItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeAPI {

    //get app version
    @GET("mobile/apps/{appType}")
    fun getItems(): Observable<RecipeItem>
}