package com.tony.imagemvvm.network.data.api.interceptor

import android.util.Log
import com.tony.imagemvvm.network.data.constants.ApiConstants
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ApiQueryInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
                .addQueryParameter("api_key", ApiConstants.API_KEY)
                .addQueryParameter("format", ApiConstants.API_FORMAT)
                .addQueryParameter("nojsoncallback", ApiConstants.API_JSON_CALLBACK)
                .build()

        Log.d("test3", url.toString())
        val request = Request.Builder().url(url).build()
        return chain.proceed(request)
    }

}