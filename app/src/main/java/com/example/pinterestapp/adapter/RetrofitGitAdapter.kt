package com.example.pinterestapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pinterestapp.databinding.HomeRecyclerItemBinding
import com.example.pinterestapp.model.ResponseItem
import com.example.pinterestapp.util.GetDetailsInfo
import com.example.pinterestapp.util.RandomColor

class RetrofitGitAdapter(var context: Context, var items: ArrayList<ResponseItem>) :
    RecyclerView.Adapter<RetrofitGitAdapter.HomeViewHolder>() {


    lateinit var onItemClick: (ResponseItem) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            HomeRecyclerItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val home = items[position]

        if (holder is HomeViewHolder) {
            val tvTitle = holder.tvTitle
            val ivPhoto = holder.ivPhoto
            val cardView = holder.cardView

            Glide.with(context)
                .load(home.urls?.thumb)
                .placeholder(RandomColor.randomColor())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivPhoto)

            tvTitle.text = home.description


            cardView.setOnClickListener {
                GetDetailsInfo.id = home.id.toString()
                GetDetailsInfo.title = home.description.toString()
                GetDetailsInfo.links = home.urls?.small.toString()
                Log.d("111@@@", home.id.toString())
                onItemClick.invoke(ResponseItem())
            }

        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class HomeViewHolder(private val binding: HomeRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var tvTitle = binding.tvTitle
        var ivPhoto = binding.itemImages1
        var cardView = binding.cardView1

    }

}