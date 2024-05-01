package com.example.flo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import me.relex.circleindicator.CircleIndicator3

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

            //fragment간 정보 전달 방법
            //bundle 형태로 데이터 입력
            bundle.apply {
                this.putString("title", "1st Album - Alexis King")
                this.putString("artist", "Alexis King")
                this.putString("metaInfo", "2021.03.25 | 정규 | 해외 팝")
            }

            //생성한 fragment에 argument로 등록
            albumFragment.arguments = bundle

            //fragment 전환할 때, 생성한 fragment로 전환해줘야 한다
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, albumFragment).commitAllowingStateLoss()
        }

        //baner viewpager 설정
        //bannerAdapter 초기화, fragment 추가
        val bannerAdapter = BannerVPApater(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter //viewpager view와 adapter 연결
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //viewpager에 좌우 스크롤 속성 추가

        //panel viewpager 설정
        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(PanelFragment())
        panelAdapter.addFragment(PanelFragment())
        binding.homePanelVp.adapter = panelAdapter

        binding.homePanelCi.setViewPager(binding.homePanelVp) //viewpager에 circleindicator 연결

        return binding.root
    }
}