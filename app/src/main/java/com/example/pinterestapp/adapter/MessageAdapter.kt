package com.example.pinterestapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pinterestapp.fragment.MessagesFragment
import com.example.pinterestapp.fragment.UpdateFragment

class MessageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            return UpdateFragment()
        }
        return MessagesFragment()
    }
}