package com.example.pinterestapp.fragment

import android.media.Image
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import com.example.pinterestapp.R
import com.example.pinterestapp.adapter.ViewPagerAdapter
import com.example.pinterestapp.databinding.ActivityMainBinding
import com.example.pinterestapp.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root

        initViews()
    }

    private fun initViews() {

        binding.tabMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_home_24)
                    }
                    1 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_search)
                    }
                    2 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_chat_24)
                    }
                    3 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_person_24)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_home)
                    }
                    1 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_search_24)
                    }
                    2 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_chat_bubble_outline_24)
                    }
                    3 -> {
                        tab.customView?.findViewById<ImageView>(R.id.image_tab_main)
                            ?.setImageResource(R.drawable.ic_baseline_person_outline_24)
                    }
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)

        binding.viewPager2.adapter = adapter
        binding.viewPager2.isUserInputEnabled = false
    }

    override fun onResume() {
        super.onResume()
        val tabLayout = binding.tabMain
        val viewPager = binding.viewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->

            val inflate =
                LayoutInflater.from(context).inflate(R.layout.item_tab_main, null, false)
            tab.customView = inflate
            val imageView: ImageView = inflate.findViewById(R.id.image_tab_main)

            when (position) {
                0 -> {
                    imageView.setImageResource(R.drawable.ic_baseline_home)
                }
                1 -> {
                    imageView.setImageResource(R.drawable.ic_baseline_search_24)
                }
                2 -> {
                    imageView.setImageResource(R.drawable.ic_baseline_chat_bubble_outline_24)
                }
                3 -> {
                    imageView.setImageResource(R.drawable.ic_baseline_person_outline_24)
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}