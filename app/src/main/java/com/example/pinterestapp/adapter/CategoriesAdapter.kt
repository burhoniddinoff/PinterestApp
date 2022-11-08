package com.example.pinterestapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pinterestapp.R
import com.example.pinterestapp.modelSearchFrag.CollectionsModelItem
import com.example.pinterestapp.util.RandomColor
import com.google.android.material.imageview.ShapeableImageView

class CategoriesAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val lists:ArrayList<CollectionsModelItem> = ArrayList()
    lateinit var photoClick: ((CollectionsModelItem) -> Unit)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.search_collections_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = lists[position]

        if (holder is MyViewHolder) {
            holder.apply {
                Glide.with(context)
                    .load(list.cover_photo.urls.small)
                    .placeholder(RandomColor.randomColor())
                    .into(imageView)

                textView.text = list.title

                relative.setOnClickListener {
                    photoClick.invoke(list)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return lists.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitData(list: List<CollectionsModelItem>){
        lists.addAll(list)
        notifyDataSetChanged()
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ShapeableImageView = view.findViewById(R.id.collections_images)
        val textView: TextView = view.findViewById(R.id.collections_text)
        val relative: RelativeLayout = view.findViewById(R.id.relative)
    }
}