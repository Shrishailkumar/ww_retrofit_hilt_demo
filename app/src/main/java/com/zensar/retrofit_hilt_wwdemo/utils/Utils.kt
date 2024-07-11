package com.zensar.retrofit_hilt_wwdemo.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.zensar.retrofit_hilt_wwdemo.WWRetrofitCachingHiltDemoApp
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.concurrent.TimeUnit

object Utils {

    fun hasInternetConnection(context: Context?): Boolean {
        if (context == null)
            return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager

        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    // if the Cache-Control is not enabled from the server? We can still cache the response from OkHttp Client using Interceptor
    class CacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request())
            val cacheControl = CacheControl.Builder()
                .maxAge(1, TimeUnit.SECONDS)
                .build()
            return response.newBuilder()
                .header("Cache-Control", cacheControl.toString())
                .build()
        }
    }

    //	 ForceCacheInterceptor in addition to the above one (CacheInterceptor, only if Cache-Control header is not enabled from the server).
    class ForceCacheInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val builder: Request.Builder = chain.request().newBuilder()
            if (!hasInternetConnection(WWRetrofitCachingHiltDemoApp.appContext)) {
                builder.cacheControl(CacheControl.FORCE_CACHE);
            }
            return chain.proceed(builder.build());
        }
    }
}