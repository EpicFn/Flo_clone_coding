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

    //MainActivity의 function을 실행시키기 위한 interface
    //MiniPlayer 조작에 사용
    interface SongSetter{
        fun setSongWithAlbum(selectedAlbum : Album)
    }

    private lateinit var mySongSetter: SongSetter
    fun setSongSetter(songSetter: SongSetter){
        mySongSetter = songSetter
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)



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

        //recyclerview의 각 item에 연결한 event Listener를 설정
        //albumRVAdapter에서 선언된 함수를 호출
        //interface event listener를 override 하여 eventListener를 생성해서 넘겨준다
        //click listener를 homeFragment에서만 생성할 수 있기 때문에
        albumRVAdapter.setMyItemClickListener(object:AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                val albumFragment = AlbumFragment()
                val bundle = Bundle()

                //fragment간 정보 전달 방법
                //bundle 형태로 데이터 입력
                bundle.apply {
                    this.putString("title", album.title)
                    this.putString("artist", album.artist)
                    this.putString("metaInfo", "2021.03.25 | 정규 | 해외 팝")
                    this.putInt("coverImg", album.coverImg!!)
                }

                //생성한 fragment에 argument로 등록
                albumFragment.arguments = bundle

                //fragment 전환할 때, 생성한 fragment로 전환해줘야 한다
                (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()

            }

            override fun onPlayBtnClick(albumIdx: Int) {
                //miniplayer 변경 코드
                //MainActivity의 setSongWithAlbum 함수를 호출한다
                mySongSetter.setSongWithAlbum(albumDatas[albumIdx])
                Log.d("play btn click", albumDatas[albumIdx].title!!)

            }


        })




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