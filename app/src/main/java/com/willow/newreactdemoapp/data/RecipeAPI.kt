package com.willow.newreactdemoapp.data

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeAPI {

    //get app version
    @GET("mobile/apps/{appType}")
    fun getAppVersion(@Path("appType") appType: String = "android"): Observable<AppVersionResponse>
}