package com.example.flo

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)



        binding.SongplayerHeaderBtnDownIv.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("title", binding.SongplayerTitleTv.text.toString())

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        //재생버튼, 정지버튼 클릭 시 전환 구현
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }


    //player의 정보를 intent로 받은 data로 초기화
    private fun setPlayer(song: Song){
        binding.SongplayerTitleTv.text = intent.getStringExtra("title")
        binding.SongplayerArtistTv.text = intent.getStringExtra("artist")
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        setPlayerStatus(song.isPlaying)
    }

    //intent로 받은 data를 song 객체에 저장
    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("artist")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("artist")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playtime", 0),
                intent.getBooleanExtra("isplaying", false))
        }
        startTimer()
    }


    //재생버튼, 정지버튼 클릭 실행하는 이미지 전환 함수
    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else{
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }

    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    //progress 측정을 위한 thread 용 내부 class
    //inner class로 생성하면 외부 class의 값에 접근이 가능하다
    inner class Timer(private val playTime : Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try{
                while(true){
                    if(second >= playTime){ // 음악이 종료되면 루프 탈출
                        break;
                    }

                    if(isPlaying){
                        sleep(50)
                        mills += 50

                        runOnUiThread {//progress 바 갱신
                            binding.songProgressSb.progress = ((mills/ playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){//시간 count 갱신
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch(e:InterruptedException){
                Log.d("Song", "Thread Dead. ${e.message}")
            }

        }
    }



}