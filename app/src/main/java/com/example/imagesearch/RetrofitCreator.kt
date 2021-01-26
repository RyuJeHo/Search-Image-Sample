package com.example.hyunsikyoo.retrofit_example_kotlin.retrofit

import com.example.imagesearch.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitCreator {

    fun getService(): RetrofitService = retrofit.create(RetrofitService::class.java)

    var logInterceptor = HttpLoggingInterceptor()

    var okHttpClient =
            OkHttpClient.Builder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .addInterceptor(logInterceptor).build()

    private val retrofit =
            Retrofit.Builder()
                    .baseUrl(RetrofitService.BASE_URL) // 도메인 주소
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create()) // GSON을 사용하기 위해 ConverterFactory에 GSON 지정
                    .build()
}