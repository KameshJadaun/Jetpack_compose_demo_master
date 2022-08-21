package com.jetpack_compose_practice.data.remote

import android.content.Context
import android.util.Log
import com.google.gson.GsonBuilder
import com.jetpack_compose_practice.utils.Config
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by kamesh singh.
 */

class ServiceGenerator() {

    private var retrofit: Retrofit
    private val client: OkHttpClient
    val clientService: ApiClient


    init {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpBuilder = OkHttpClient.Builder()

        okHttpBuilder.connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)


        val logging = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        okHttpBuilder.addInterceptor(logging)

        okHttpBuilder.addInterceptor { chain ->
            val original = chain.request()
            var request: Request? = null


                request = original.newBuilder()
                    .header("User-Agent", "JetpackPractice")
                    .header("Accept", "application/vnd.yourapi.v1.full+json")
                    .header("Content-Type", "application/json")
                   // .header("Accept-Language", "${language ?: "en"}")
                    .method(original.method, original.body)
                    .build()


            chain.proceed(request)
        }

        client = okHttpBuilder.build()
        retrofit = Retrofit.Builder().baseUrl(Config.BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().excludeFieldsWithoutExposeAnnotation().create())).build()


        clientService = retrofit.create(ApiClient::class.java)
    }

    companion object {
       private var instance: ServiceGenerator? = null
            get() {
                if (field == null) {
                    field = ServiceGenerator()
                }
                return field
            }
            private set



        fun getInstanse(): ServiceGenerator {

            if (instance == null) {
                instance =
                    ServiceGenerator()
            }

            return instance as ServiceGenerator
        }
    }


}