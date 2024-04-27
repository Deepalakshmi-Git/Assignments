package com.example.androidtestapp

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

          //  mBinding.recyclerView=

        })
    }

    override fun onItemClick(userId: String, id: String, title: String, body: String) {
    }

    override fun onApiCall() {
        mSkip += 10
        mDataViewModel.getData(mSkip)

    }

    /* override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         enableEdgeToEdge()
         setContentView(R.layout.activity_main)
         ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
             val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
             v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
             insets
         }

         mDataAdapter=
         val mDataViewModel= ViewModelProvider(this).get(DataViewModel::class.java)
         mDataViewModel.getData(10)


         mDataViewModel.onGetData().observe(this,{
             Log.d("MediaDetails","Observed"+it)

             mJobsAdapter.updateData(it, mViewType, mSkip)
         })

     }*/
}