package com.example.flo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song:Song = Song()
    private val gson:Gson = Gson()


    //Song-activity result를 handling 하는 함수
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result ->
        if(result.resultCode == Activity.RESULT_OK){//result가 있는 경우
            val resultString = result.data?.getStringExtra("title").toString()
            //Toast 메세지 출력
            Toast.makeText(this, resultString, Toast.LENGTH_SHORT).show();
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //미니플레이어에 이벤트리스너 연결
        //result를 받는 형태로 호출
        binding.mainPlayerCl.setOnClickListener{
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("artist", song.artist)
            intent.putExtra("second", song.second)
            intent.putExtra("playtime", song.playTime)
            intent.putExtra("isplaying", song.isPlaying)
            intent.putExtra("music", song.music)

            //result를 받는 형태로 intent를 보내면서 activity 호출
            getResultText.launch(intent)
        }
        initBottomNavigation()


    }

    //miniplayer를 설정하는 함수
    private fun setMiniPlayer(song:Song){
        binding.mainMiniPlayerTitleTv.text = song.title
        binding.mainMiniPlayerArtistTv.text = song.artist
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
    }

    override fun onStart() {
        super.onStart()

        //sharedPreference에서 실행되던 song data 받아오기
        //받아온 값을 song 객체로 변환
        val sharedPreferneces = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferneces.getString("songData", null)

        song = if(songJson == null){
            Song("Surrender", "Alexis King", 0, 60, false, "sample_music_1")
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)

    }

    //bottom navigation 설정을 별도 함수로 구현
    private fun initBottomNavigation(){

        //앱 초기화 시 메인 화면을 homeFragment로 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()


        //main의 bottom navigation의 eventlistenr 설정
        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}