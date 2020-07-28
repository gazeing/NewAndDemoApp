package com.sonder.newdemoapp.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sonder.newdemoapp.data.RecipeAPI
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit

const val baseUrl = "https://spending-7dc19.firebaseio.com/"
const val TIMEOUT_REQUEST: Long = 30

@Module
@InstallIn(ApplicationComponent::class)
object ApiProvider {

    @Provides
    fun providePicasso(): Picasso = Picasso.get()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(
        )
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_REQUEST, TimeUnit.SECONDS)
            .addNetworkInterceptor(logger)
        try {
            builder
                .sslSocketFactory(TLSSocketFactory(), BlindTrustManager())

        } catch (e: KeyManagementException) {
            Log.e("HTTP", e.message)
        } catch (e: NoSuchAlgorithmException) {
            Log.e("HTTP", e.message)
        }

        return builder.build()
    }

    fun createRecipeAPI(url: String = baseUrl): RecipeAPI {
        return Retrofit.Builder()
            .client(provideHttpClient(provideHttpLoggingInterceptor()))
            .addConverterFactory(GsonConverterFactory.create(provideGson()))
            .baseUrl(url).build().create(RecipeAPI::class.java)
    }
}