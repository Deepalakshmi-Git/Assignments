package com.example.androidtestapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtestapp.adapter.DataAdapter
import com.example.androidtestapp.databinding.ActivityMainBinding
import com.example.androidtestapp.repository.viewmodels.DataViewModel

open class MainActivity : BaseAppCompactActivity<ActivityMainBinding>(),DataListener {
    private val mDataAdapter by lazy { DataAdapter(this) }
    private var mSkip = 0

    private val mDataViewModel by lazy {
        ViewModelProvider(this)[DataViewModel::class.java]
    }

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override val layoutId: Int
        get() = R.layout.activity_main

    override val isMakeStatusBarTransparent: Boolean
        get() = false

    override fun init() {

        val layoutManager= LinearLayoutManager(this)
        mBinding.recyclerView.layoutManager = layoutManager
        mBinding.recyclerView.adapter = mDataAdapter

        mDataViewModel.getData(mSkip)
        initObserver()
    }

    fun initObserver()
    {
        mDataViewModel.onGetData().observe(this,{
            Log.d("Data","Found"+it)
            mDataAdapter.updateData(it)
        })
    }



    override fun onItemClick(userId: String?, id: String?, title: String?, body: String?) {
        val intent = Intent(this@MainActivity, DetailedView::class.java)
        intent.putExtra("Title", title)
        intent.putExtra("Body",body)
        startActivity(intent)
    }

    override fun onApiCall() {
        mSkip += 10
        mDataViewModel.getData(mSkip)

    }
}