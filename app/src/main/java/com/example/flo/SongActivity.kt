package com.example.flo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
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
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", binding.SongplayerTitleTv.text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        //재생버튼, 정지버튼 클릭 시 전환 구현
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        //intent로 넘어온 값으로 song activity의 정보를 구성
        if(intent.hasExtra("title") && intent.hasExtra("artist")){
            binding.SongplayerTitleTv.text = intent.getStringExtra("title")
            binding.SongplayerArtistTv.text = intent.getStringExtra("artist")
        }


    }

    //재생버튼, 정지버튼 클릭 실행하는 이미지 전환 함수
    private fun setPlayerStatus(isPlaying : Boolean){
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