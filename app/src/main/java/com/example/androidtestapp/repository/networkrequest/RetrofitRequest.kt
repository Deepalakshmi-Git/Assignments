package com.example.androidtestapp.repository.networkrequests

import com.example.androidtestapp.R
import com.example.androidtestapp.repository.models.PojoNetworkResponse
import com.example.androidtestapp.repository.networkrequests.RestClient
import okhttp3.ResponseBody
import java.io.IOException

object RetrofitRequest {


    fun checkForResponseCode(code: Int): PojoNetworkResponse {
        return when (code) {
            200 -> PojoNetworkResponse(isSuccess = true, isSessionExpired = false)
            201 -> PojoNetworkResponse(isSuccess = true, isSessionExpired = false)
            401 -> PojoNetworkResponse(isSuccess = false, isSessionExpired = true)
            else -> PojoNetworkResponse(isSuccess = false, isSessionExpired = false)
        }
    }

    fun getErrorMessage(responseBody: ResponseBody): String {
        val errorMessage = ""
        try {
            val errorConverter = RestClient.retrofitInstance!!
                .responseBodyConverter<Error>(Error::class.java, arrayOfNulls<Annotation>(0))
            return errorConverter.convert(responseBody)?.message ?: ""
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return errorMessage
    }

    fun getRetrofitError(t: Throwable): Int {
        return if (t is IOException) {
            R.string.no_internet
        } else {
            R.string.retrofit_failure
        }
    }

}