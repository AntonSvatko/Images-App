package com.tony.imagemvvm.network.data.api

import android.content.Context
import com.tony.imagemvvm.network.data.api.interceptor.ApiQueryInterceptor
import com.tony.imagemvvm.network.data.api.interceptor.ConnectivityInterceptor
import com.tony.imagemvvm.network.data.constants.ApiConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoServiceClient {
    fun getClient(context: Context): PhotosService {
        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(ConnectivityInterceptor(context))
                .addInterceptor(ApiQueryInterceptor())
                .build()

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(ApiConstants.API_LINK)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PhotosService::class.java)
    }
}