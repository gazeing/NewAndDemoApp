package com.willow.newreactdemoapp.data

import com.willow.newreactdemoapp.model.RecipeItem
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface RecipeAPI {

    //get recipes
    //https://spending-7dc19.firebaseio.com/receipes.json?orderBy="receipeOrder"&limitToFirst=5&print=pretty
    @GET("receipes.json?orderBy=\"receipeOrder\"&limitToFirst=5&print=pretty")
    fun getItems(): Single<List<RecipeItem>>
}