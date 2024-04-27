package com.example.androidtestapp.repository.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.androidtestapp.R
import com.example.androidtestapp.adapter.DataAdapter.Companion.LIMIT
import com.example.androidtestapp.repository.interators.DataInteractor
import com.example.androidtestapp.repository.models.DataResponse
import com.example.androidtestapp.repository.models.RetrofitErrorMessage
import com.example.androidtestapp.repository.networkrequests.NetworkRequestCallbacks
import com.example.androidtestapp.repository.networkrequests.RetrofitRequest
import retrofit2.Response

class DataViewModel (application: Application) : BaseViewModel(application) {

    private var mData = MutableLiveData<List<DataResponse>>()

    private val mDataInteractor by lazy {
        DataInteractor()
    }

    fun getData(start:Int,showLoader: Boolean = true) {
        Log.d("MediaDetails","Called")
        if (showLoader) {
            isShowLoader.value = true
        }
        mCompositeDisposable.add(
            mDataInteractor.getData(start,LIMIT,
                object :
                    NetworkRequestCallbacks {
                    override fun onSuccess(response: Response<*>) {
                        try {
                            isShowLoader.value = false
                            val pojoNetworkResponse =
                                RetrofitRequest.checkForResponseCode(response.code())
                            when {
                                pojoNetworkResponse.isSuccess && null != response.body() -> {
                                    val mResponse =
                                        response.body() as List<DataResponse>
                                    mData.value = mResponse
                                }

                                pojoNetworkResponse.isSessionExpired -> {
                                    isSessionExpired.value = true
                                }


                                else -> {
                                    retrofitErrorMessage
                                        .postValue(
                                            RetrofitErrorMessage(
                                                errorMessage =
                                                RetrofitRequest.getErrorMessage(
                                                    response.errorBody()!!
                                                )
                                            )
                                        )
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                            retrofitErrorMessage
                                .postValue(
                                    RetrofitErrorMessage(
                                        errorResId =
                                        R.string.retrofit_failure
                                    )
                                )
                        }
                    }

                    override fun onError(t: Throwable) {
                        t.printStackTrace()
                        isShowLoader.value = false
                        retrofitErrorMessage
                            .postValue(
                                RetrofitErrorMessage(
                                    errorResId =
                                    RetrofitRequest.getRetrofitError(t)
                                )
                            )
                    }

                })
        )
    }

    fun onGetData() = mData
}