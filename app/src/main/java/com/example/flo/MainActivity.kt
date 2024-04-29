package com.example.flo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.flo.databinding.ActivityMainBinding
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding


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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val song = Song(binding.homeMiniPlayerTitleTv.text.toString(), binding.homeMiniPlayerArtistTv.text.toString())

        //미니플레이어에 이벤트리스너 연결
        //result를 받는 형태로 호출
        binding.mainPlayerCl.setOnClickListener{
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("artist", song.artist)

            //result를 받는 형태로 intent를 보내면서 activity 호출
            getResultText.launch(intent)
        }
        initBottomNavigation()


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