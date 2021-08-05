package com.example.moviemaster.data.remote

import com.example.moviemaster.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private val apiClient = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(getHttpClient())
        .build()
        .create(MovieService::class.java)

    private fun getHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        return OkHttpClient()
            .newBuilder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(TokenInterceptor())
            .build()
    }

    fun buildMoviesService(): MovieService{
        return apiClient
    }

    class TokenInterceptor() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            var original = chain.request()
            val url = original.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
            original = original.newBuilder().url(url).build()
            return chain.proceed(original)
        }
    }
}