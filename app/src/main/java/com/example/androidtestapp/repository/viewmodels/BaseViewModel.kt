package com.example.androidtestapp.repository.viewmodels

import android.app.Application
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.repository.models.RetrofitErrorMessage
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected val isShowLoader = MutableLiveData<Boolean>()
    protected val isShowNoDataText = MutableLiveData<Boolean>()
    protected val isShowSwipeRefreshLayout = MutableLiveData<Boolean>()
    protected val isSessionExpired = MutableLiveData<Boolean>()
    protected val retrofitErrorDataMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val retrofitErrorMessage = MutableLiveData<RetrofitErrorMessage>()
    protected val successMessage = MutableLiveData<String>()
    protected val mCompositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable() }

    fun isShowLoader(): LiveData<Boolean> = isShowLoader

    fun isShowNoDataText(): LiveData<Boolean> = isShowNoDataText

    fun isSessionExpired(): LiveData<Boolean> = isSessionExpired

    fun isShowSwipeRefreshLayout(): LiveData<Boolean> = isShowSwipeRefreshLayout

    fun getRetrofitErrorDataMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorDataMessage

    fun getRetrofitErrorMessage(): LiveData<RetrofitErrorMessage> = retrofitErrorMessage


    fun getSuccessMessage(): LiveData<String> = successMessage



    interface ErrorEvent {
        @StringRes
        fun getErrorResource(): Int
    }

}