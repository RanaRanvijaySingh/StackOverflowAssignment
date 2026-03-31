package com.example.stackoverflow.data.remote.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        Log.d(TAG, "Request -> ${request.method} ${request.url}")

        val response = chain.proceed(request)
        Log.d(TAG, "Response <- ${response.code} ${response.request.url}")

        return response
    }

    companion object {
        private const val TAG = "NetworkLogger"
    }
}
