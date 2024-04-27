package com.example.androidtestapp.repository.interators

import android.util.Log
import com.example.androidtestapp.repository.networkrequests.RestClient
import com.example.androidtestapp.repository.networkrequests.NetworkRequestCallbacks
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class DataInteractor {

    fun getData(start:Int,limit:Int,
        networkRequestCallbacks: NetworkRequestCallbacks
    ): Disposable {
        return RestClient.get().getData(start,limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<Response<*>>() {
                override fun onNext(response: Response<*>) {
                    Log.d("MediaDetails","Success")
                    networkRequestCallbacks.onSuccess(response)
                }

                override fun onError(t: Throwable) {
                    Log.d("MediaDetails","Error")
                    networkRequestCallbacks.onError(t)
                }

                override fun onComplete() {

                }
            })
    }
}