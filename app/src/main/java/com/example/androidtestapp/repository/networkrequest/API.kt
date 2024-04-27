package com.example.androidtestapp.repository.networkrequests


import com.example.androidtestapp.repository.models.DataResponse
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface API {


    @GET("/posts")
    fun getData(
        @Query("_start") _start: Int,
        @Query("_limit") _limit: Int,
    ): Observable<Response<List<DataResponse>>>
}