package com.example.pinterestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.pinterestapp.R
import com.example.pinterestapp.adapter.MessageAdapter
import com.google.android.material.tabs.TabLayout

class MessageFragment : Fragment() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_message, container, false)
        initViews(view)
        return view
    }

    private fun initViews(view: View) {
        viewPager2 = view.findViewById(R.id.view_pager2)
        tabLayout = view.findViewById(R.id.tabLayout)
        messageAdapter = MessageAdapter(childFragmentManager, lifecycle)
        viewPager2.adapter = messageAdapter

        tabLayout.addTab(tabLayout.newTab().setText("Update"))
        tabLayout.addTab(tabLayout.newTab().setText("Message"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager2.currentItem = tab!!.position

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })
    }
}