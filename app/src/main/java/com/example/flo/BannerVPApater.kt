package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//fragmentStateAdapter를 상속받는다
//parent class의 method를 override해서 사용
class BannerVPApater(fragment:Fragment) :FragmentStateAdapter(fragment) {

    //fragment를 저장할 배열
    private val fragmentlist : ArrayList<Fragment> = ArrayList()

    //class에 연결된 viewpager에 item을 전달할 때, 전달할 개수를 명시
    override fun getItemCount(): Int = fragmentlist.size //list에 담긴 fragment 개수 만큼 반환

    //fragment를 생성해주는 함수
    //position은 0부터 getItemCount의 반환값만큼 돈다
    override fun createFragment(position: Int): Fragment = fragmentlist[position] //list의 값을 하나씩 반환

    //fragmentList 초기화용 함수
    fun addFragemenbt(fragment: Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) //item의 개수를 알려주는 method
    }


}