package com.example.pinterestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.pinterestapp.R
import com.example.pinterestapp.util.GetDetailsInfo
import com.example.pinterestapp.util.RandomColor
import com.ortiz.touchview.TouchImageView

class FullScreenFragment : Fragment() {

    private lateinit var imageView: TouchImageView
    private lateinit var backBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_full_screen, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        imageView = view.findViewById(R.id.imageView)
        backBtn = view.findViewById(R.id.back_btn)

        Glide.with(view)
            .load(GetDetailsInfo.links)
            .placeholder(RandomColor.randomColor())
            .into(imageView)

        backBtn.setOnClickListener {
            findNavController().navigate(R.id.action_fullScreenFragment_to_detailFragment)
        }
    }
}