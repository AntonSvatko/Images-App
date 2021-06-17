package com.tony.imagemvvm.network.data.api.interceptor

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import com.tony.imagemvvm.network.data.exeptions.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

class ConnectivityInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (isNetworkConnected()) chain.proceed(chain.request()) else throw NoConnectivityException()
    }

    private fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < 23) {
            val ni = cm.activeNetworkInfo
            if (ni != null) {
                return ni.isConnected && (ni.type == ConnectivityManager.TYPE_WIFI || ni.type == ConnectivityManager.TYPE_MOBILE)
            }
        } else {
            val n: Network? = cm.activeNetwork
            if (n != null) {
                val nc = cm.getNetworkCapabilities(n)
                return nc!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || nc.hasTransport(
                    NetworkCapabilities.TRANSPORT_WIFI
                )
            }
        }

        return false
    }
}