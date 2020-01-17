package com.sonder.newdemoapp.di

import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.sonder.newdemoapp.data.RecipeAPI
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.net.ssl.X509ExtendedTrustManager
import javax.net.ssl.X509TrustManager

const val baseUrl = "https://spending-7dc19.firebaseio.com/"
const val TIMEOUT_REQUEST: Long = 30

class ApiProvider {

    fun provideGson(): Gson = GsonBuilder().create()

    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(
        )
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

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
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url).build().create(RecipeAPI::class.java)
    }
}