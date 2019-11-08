package com.willow.newreactdemoapp.data

import com.willow.newreactdemoapp.model.RecipeItem
import io.reactivex.Observable
import retrofit2.http.GET

interface RecipeAPI {

    //get recipes
    //https://spending-7dc19.firebaseio.com/receipes.json?orderBy="receipeOrder"&limitToFirst=5&print=pretty
    @GET("receipes.json?orderBy=\"receipeOrder\"&limitToFirst=5&print=pretty")
    fun getItems(): Observable<RecipeItem>
}