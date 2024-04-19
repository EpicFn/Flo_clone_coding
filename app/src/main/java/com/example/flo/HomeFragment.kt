package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.homeTodayMusicAlbum1.setOnClickListener{
            val albumFragment = AlbumFragment()
            val bundle = Bundle()

            bundle.apply {
                this.putString("title", "1st Album - Alexis King")
                this.putString("artist", "Alexis King")
                this.putString("metaInfo", "2021.03.25 | 정규 | 해외 팝")
            }
            albumFragment.arguments = bundle


            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()
        }

        return binding.root
    }
}