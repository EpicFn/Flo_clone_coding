package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class LockerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 2

    override fun createFragment(p0: Int): Fragment {
        return when(p0){
            0 -> SavedmusicFragment()
            else -> MusicfileFragment()
        }
    }

}