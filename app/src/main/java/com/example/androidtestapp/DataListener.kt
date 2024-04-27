package com.example.androidtestapp

interface DataListener {
    fun onItemClick(userId: String?, id: String?, title: String? = "", body: String?= "")
    fun onApiCall()
}