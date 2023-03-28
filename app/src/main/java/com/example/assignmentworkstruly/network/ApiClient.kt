package com.example.assignmentworkstruly.network


import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    val gson = GsonBuilder().setLenient().create()

    fun getRetrofitService(context: Context): ApiInterface {
        val myClient = getClient(context)
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(myClient!!)
            .build().create(ApiInterface::class.java)
    }


    private fun getClient(context: Context): OkHttpClient? {

        val headerInterceptor = Interceptor { chain ->
            val original: Request = chain.request()
            val request: Request = original.newBuilder()
                .method(original.method, original.body)
                .build()
            val response = chain.proceed(request)
            response

        }


        val logingInterceptor = HttpLoggingInterceptor()
        logingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addNetworkInterceptor(headerInterceptor)
            .addNetworkInterceptor(logingInterceptor)
            .build()
        return client
    }


}