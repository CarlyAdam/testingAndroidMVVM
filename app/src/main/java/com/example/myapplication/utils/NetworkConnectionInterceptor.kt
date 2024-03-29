package com.example.myapplication.utils

import android.content.Context
import android.net.ConnectivityManager
import com.example.myapplication.R
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(
    context: Context
) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if(!checkConnection())
            throw NoInternetException(applicationContext.resources.getString(R.string.no_connection))
        return chain.proceed(chain.request())
    }

    private fun checkConnection() : Boolean{
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.activeNetworkInfo.also {
            return it != null && it.isConnected
        }
    }

}