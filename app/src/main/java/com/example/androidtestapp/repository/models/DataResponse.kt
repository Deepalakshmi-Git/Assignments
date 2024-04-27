package com.example.androidtestapp.repository.models

import com.google.gson.annotations.SerializedName

data class DataResponse(
    @SerializedName("userId") var userId: String?= null,
    @SerializedName("id") var id: String?= null,
    @SerializedName("title") var title: String?= null,
    @SerializedName("body") var body: String?= null
)
