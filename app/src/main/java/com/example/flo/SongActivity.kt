package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.SongplayerHeaderBtnDownIv.setOnClickListener{
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }

        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        if(intent.hasExtra("title") && intent.hasExtra("artist")){
            binding.SongplayerTitleTv.text = intent.getStringExtra("title")
            binding.SongplayerArtistTv.text = intent.getStringExtra("artist")
        }

    }

    fun setPlayerStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }

    }


}