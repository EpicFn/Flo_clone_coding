package com.example.flo

import android.content.ClipData.Item
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.flo.databinding.ItemAlbumBinding

//album recycular view를 binding 해주는 adapter
class AlbumRVAdapter(private val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumRVAdapter.viewHolder>() {

    //eventl listener 설정을 위한 interface 정의
    interface MyItemClickListener{
        //recycler view에 click listener가 없기 때문에 정의해준다
        //album data를 받기 위해 매개변수 사용
        fun onItemClick(album: Album)
    }

    //interface 객체 선언
    //외부(home fragment)에서 eventListener 객체를 전달받아서 저장한다
    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    //viewholder를 생성하는 함수
    //초기에만 호출된다
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): AlbumRVAdapter.viewHolder {
        //사용할 item view이 binding 객체를 생성한다
        val binding:ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        //생성한 binding 객체를 재활용할 수 있게 view holder에 던져준다
        return viewHolder(binding)
    }

    //viewholder에 data를 binding할 때마다 호출되는 함수
    //p1 : position. item의 index 값
    override fun onBindViewHolder(p0: AlbumRVAdapter.viewHolder, p1: Int) {
        //선택된 position에 item view를 던져준다
        //받아온 item 객체를 itemview 객체에 넣어준다
        p0.bind(albumList[p1])

        //itemView에 eventListener 연결
        //evetn Listener 객체는 homeFragment에서 가져온다
        p0.itemView.setOnClickListener{myItemClickListener.onItemClick(albumList[p1])}
    }


    //item의 개수를 반환하는 함수
    override fun getItemCount(): Int = albumList.size

    //viewholer 정의
    //매개 변수로 Item view의 객체를 받아야 한다
    inner class viewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(album: Album){
            binding.itemAlbumTitleTv.text  = album.title
            binding.itemAlbumArtistTv.text = album.artist
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }

}