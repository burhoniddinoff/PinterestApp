package com.example.pinterestapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.pinterestapp.R
import com.example.pinterestapp.modelSearch.Result
import com.example.pinterestapp.util.GetDetailsInfo
import com.example.pinterestapp.util.RandomColor

class DetailAdapter(private val context: Context, private val lists: ArrayList<Result>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var itemCLick: ((Result) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.home_recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = lists[position]

        if (holder is MyViewHolder) {
            holder.apply {
                textView.text = list.description
                Glide.with(context)
                    .load(list.urls?.small)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(RandomColor.randomColor())
                    .into(imageView)
            }
            holder.carView.setOnClickListener {
                GetDetailsInfo.id = list.id
                GetDetailsInfo.title = list.description.toString()
                GetDetailsInfo.links = list.urls!!.small
                itemCLick.invoke(list)
            }
        }
    }


    override fun getItemCount(): Int {
        return lists.size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.item_images1)
        val textView: TextView = view.findViewById(R.id.tv_title)
        val carView: CardView = view.findViewById(R.id.card_view1)
    }
}