package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment() {

    lateinit var binding : FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        //내취향 버튼 이벤트리스너 연결
        binding.songMixoffTg.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songMixonTg.setOnClickListener{
            setPlayerStatus(true)
        }


        return binding.root
    }

    //내취향 버튼 클릭 시 실행하는 이미지 전환 함수
    private fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMixoffTg.visibility = View.VISIBLE
            binding.songMixonTg.visibility = View.GONE
        }
        else{
            binding.songMixonTg.visibility = View.VISIBLE
            binding.songMixoffTg.visibility = View.GONE
        }
    }
}