package com.example.androidtestapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.androidtestapp.databinding.ActivityDetailedViewBinding
import com.example.androidtestapp.databinding.ActivityMainBinding

class DetailedView : BaseAppCompactActivity<ActivityDetailedViewBinding>() {

    override fun getViewBinding() = ActivityDetailedViewBinding.inflate(layoutInflater)

    override val layoutId: Int
        get() = R.layout.activity_detailed_view

    override val isMakeStatusBarTransparent: Boolean
        get() = false


    override fun init() {
        mBinding.dettoolbarView.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_back_primary)
        mBinding.dettoolbarView.setNavigationOnClickListener { onBackPressed() }

        val title = intent.getStringExtra("Title")
        val body = intent.getStringExtra("Body")

        mBinding.txtDetTitle.text=title
        mBinding.txtDetBody.text=body
    }
}