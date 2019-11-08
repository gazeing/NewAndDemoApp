package com.willow.newreactdemoapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.willow.newreactdemoapp.data.RecipeAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

const val baseUrl = "https://spending-7dc19.firebaseio.com/"

class ApiProvider {

    fun provideGson(): Gson = GsonBuilder().create()

    fun createRecipeAPI(): RecipeAPI {
        return Retrofit.Builder()
//            .client(client)
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl).build().create(RecipeAPI::class.java)
    }
}