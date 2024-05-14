package com.example.flo

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val albumDatas = ArrayList<Album>() //album 데이터를 담는 list

    var panelPosition = 0; //panel viewpager 자동 슬라이드를 위한 변수

    //panel 자동 슬라이드 구현 : 핸들러 설정
    val handler=Handler(Looper.getMainLooper()){
        setPage()
        true
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

//        binding.homeTodayMusicAlbum1.setOnClickListener{
//            val albumFragment = AlbumFragment()
//            val bundle = Bundle()
//
//            //fragment간 정보 전달 방법
//            //bundle 형태로 데이터 입력
//            bundle.apply {
//                this.putString("title", "1st Album - Alexis King")
//                this.putString("artist", "Alexis King")
//                this.putString("metaInfo", "2021.03.25 | 정규 | 해외 팝")
//            }
//
//            //생성한 fragment에 argument로 등록
//            albumFragment.arguments = bundle
//
//            //fragment 전환할 때, 생성한 fragment로 전환해줘야 한다
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()
//        }

        // 데이터 리스트용 더미 데이터
        albumDatas.apply{
            add(Album("Surrender", "Alexis King", R.drawable.img_home_album))
            add(Album("Lilac", "IU", R.drawable.img_album_exp2))
            add(Album("Butter", "BTS", R.drawable.img_album_exp))
            add(Album("Closer", "The ChainSmokers", R.drawable.img_album_exp3))
            add(Album("Lost starts", "Adam Levine", R.drawable.img_album_exp4))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas) //adapter와 data list 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter //recycler view에 adapter 연결
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false) //recycler view에 layout manager 연결




        //baner viewpager 설정
        //bannerAdapter 초기화, fragment 추가
        val bannerAdapter = BannerVPApater(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter //viewpager view와 adapter 연결
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //viewpager에 좌우 스크롤 속성 추가

        //panel viewpager 설정
        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default, getString(R.string.panel_pharse_default)))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_album_1, getString(R.string.panel_pharse_1)))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_album_2, getString(R.string.panel_pharse_2)))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_album_3, getString(R.string.panel_pharse_3)))
        binding.homePanelVp.adapter = panelAdapter

        binding.homePanelCi.setViewPager(binding.homePanelVp) //viewpager에 circleindicator 연결

        //panel 자동 슬라이드 구현 : thread 시작
        val thread = Thread(PagerRunnable())
        thread.start()


        return binding.root
    }

    //panel 자동 슬라이드 구현 : page 넘기기
    fun setPage(){
        if(panelPosition == 4) panelPosition = 0
        binding.homePanelVp.setCurrentItem(panelPosition, true)
        panelPosition += 1
    }

    //panel 자동 슬라이드 구현 : 2초마다 페이지 넘기기
    inner class PagerRunnable : Runnable{
        override fun run() {
            while(true){
                Thread.sleep(3000)
                handler.sendEmptyMessage(0)
            }
        }
    }
}