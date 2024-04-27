package com.example.androidtestapp.adapter

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtestapp.DataListener
import com.example.androidtestapp.GeneralFunctions
import com.example.androidtestapp.databinding.LoadDataBinding
import com.example.androidtestapp.databinding.RowLoadMoreBinding
import com.example.androidtestapp.repository.models.DataResponse

open class DataAdapter(var context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mShowFullDetail = true
    private var mList = ArrayList<DataResponse>()
    private var isLoadMore = false
    private var expandedSize =  ArrayList<Int>()

    companion object {
        private const val VIEW_ITEM = 1
        private const val VIEW_MORE = 2
        const val LIMIT = 10
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_ITEM) {
            ListViewHolder(LoadDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            MoreViewHolder(RowLoadMoreBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount(): Int {
        return mList.size + 1
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(list: List<DataResponse>? = null) {

        list?.let {
            mList.addAll(it)
            isLoadMore = it.size >= LIMIT
        }
        setCellSize()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            mList.size -> VIEW_MORE
            else -> VIEW_ITEM
        }
    }

    // Set the expanded view size to 0, because all expanded views are collapsed at the beginning
    private fun setCellSize() {
        expandedSize = ArrayList()
        for (i in 0 until mList.count()) {
            expandedSize.add(0)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, i: Int) {
        if (VIEW_MORE == getItemViewType(i)) {
            (holder as MoreViewHolder).bindListView()
        } else {
            (holder as ListViewHolder).bindListView()
        }
    }

    private inner class ListViewHolder(val binding: LoadDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindListView() {
            val item = mList[adapterPosition]
            binding.txtTitleVal.text=item.title
            binding.txtIDVal.text=item.id
            binding.txtBody.text=item.body
            binding.detailLay.visibility=View.GONE
            binding.view1.visibility=View.GONE
            //collapse(binding.detailLay)

            binding.detailLay.layoutParams.height = expandedSize[position]

             binding.tvMore.setOnClickListener {
                           if(expandedSize[position]==0)
                           {
                               expand(binding.detailLay)
                               binding.detailLay.visibility=View.VISIBLE
                               binding.view1.visibility=View.VISIBLE
                               expandedSize[position] = binding.detailLay.height
                           }
                           else
                           {
                               collapse(binding.detailLay)
                               binding.detailLay.visibility=View.GONE
                               binding.view1.visibility=View.GONE
                               expandedSize[position] = 0
                           }

                         /*  if (expandedSize[position] == 0) {
                               binding.detailLay.visibility=View.VISIBLE
                               binding.view1.visibility=View.VISIBLE
                    changeViewSizeWithAnimation(
                        binding.detailLay,
                        200,
                        300L
                    )
                    expandedSize[position] = 200
                } else {
                               binding.detailLay.visibility=View.GONE
                               binding.view1.visibility=View.GONE
                    changeViewSizeWithAnimation(binding.detailLay, 0, 300L)
                    expandedSize[position] = 0

                }*/
            }

        }
    }


    private inner class MoreViewHolder(val binding: RowLoadMoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindListView() {
            if (GeneralFunctions.isInternetConnected(context)) {
                if (isLoadMore) {
                    binding.llLoadMore.visibility = View.VISIBLE
                   /* Handler(Looper.getMainLooper()).postDelayed({

                    }, 2000)*/
                    (context as DataListener).onApiCall()

                } else {
                    binding.llLoadMore.visibility = View.GONE
                }
            }
        }
    }

    fun expand(v: View){
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val targetHeight: Int = v.measuredHeight

        v.layoutParams.height = 1
        v.visibility = View.VISIBLE

        val a: Animation = object : Animation(){
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    LinearLayout.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()

            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    fun collapse(v: View) {
        val initialHeight : Int = v.measuredHeight
        val a : Animation = object : Animation(){
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f){
                    v.visibility = View.GONE
                }else{
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
        v.startAnimation(a)
    }

    private fun changeViewSizeWithAnimation(view: View, viewSize: Int, duration: Long) {
        val startViewSize = view.measuredHeight
        val endViewSize: Int =
            if (viewSize < startViewSize) (viewSize) else (view.measuredHeight + viewSize)
        val valueAnimator = ValueAnimator.ofInt(startViewSize, endViewSize)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

}