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



        //fragment 전환 시 확인
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_frm)
            Log.d("fragment", "변경됨")

            // currentFragment가 HomeFragment인지 확인
            if (currentFragment is HomeFragment) {
                currentFragment.setSongSetter(object : HomeFragment.SongSetter{
                    override fun setSongWithAlbum(selectedAlbum: Album) {
                        setSong(selectedAlbum)
                    }
                })
            }
        }



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
            intent.putExtra("coverImg", song.coverImg)

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

    //song 객체를 설정하는 함수
    //Album의 첫번째 song을 실행시킨다
    //Album 객체를 parameter로 받는다
    //progress는 전부 0으로 설정
    //mini player를 설정한다
    private fun setSong(selectedAlbum : Album){
        song = Song(selectedAlbum.title!!,
            selectedAlbum.artist!!,
            0, 60, false, "sample_music_1",
            selectedAlbum.coverImg
            )

        setMiniPlayer(song)
    }

    override fun onStart() {
        super.onStart()

        //sharedPreference에서 실행되던 song data 받아오기
        //받아온 값을 song 객체로 변환
        val sharedPreferneces = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferneces.getString("songData", null)

        song = if(songJson == null){
            Song("Surrender", "Alexis King", 0, 60, false, "sample_music_1", R.drawable.img_home_album)
        } else {
            gson.fromJson(songJson, Song::class.java)
        }

        setMiniPlayer(song)



    }



    //bottom navigation 설정을 별도 함수로 구현
    private fun initBottomNavigation(){

        var homeFragment = HomeFragment()

        //songSetter를 설정
        homeFragment.setSongSetter(object:HomeFragment.SongSetter{
            override fun setSongWithAlbum(selectedAlbum: Album) {
                setSong(selectedAlbum)
            }

        })

        //앱 초기화 시 메인 화면을 homeFragment로 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, homeFragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()


        //main의 bottom navigation의 eventlistenr 설정
        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    homeFragment = HomeFragment()

                    homeFragment.setSongSetter(object:HomeFragment.SongSetter{
                        override fun setSongWithAlbum(selectedAlbum: Album) {
                            setSong(selectedAlbum)
                        }

                    })

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, homeFragment)
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