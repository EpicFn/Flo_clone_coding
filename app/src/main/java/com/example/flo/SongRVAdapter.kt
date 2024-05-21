package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemLockedmusicBinding

//song recyculer view를 binding 해주는 adapter
class SongRVAdapter(private val songList: ArrayList<Song>):RecyclerView.Adapter<SongRVAdapter.viewHolder>() {

    interface MyItemClickListener{

        //more btn click listener
        fun onMoreBtnClick(idx : Int)
    }

    private lateinit var myItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        myItemClickListener = itemClickListener
    }

    //list에서 삭제하는 함수
    fun removeItem(position : Int){
        songList.removeAt(position)
        notifyDataSetChanged()
    }

    //viewholder를 생성하는 함수
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): SongRVAdapter.viewHolder {
        //사용할 item view의 binding 객체를 생성
        val binding:ItemLockedmusicBinding = ItemLockedmusicBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return viewHolder(binding)
    }

    //viewHolder에 data를 binding 하는 함수
    override fun onBindViewHolder(p0: SongRVAdapter.viewHolder, p1: Int) {
        //position에 item view를 던져준다
        p0.bind(songList[p1])

        p0.binding.itemLockedmusicMoreBtnIv.setOnClickListener{
            myItemClickListener.onMoreBtnClick(p1)
        }

    }

    override fun getItemCount(): Int = songList.size

    inner class viewHolder(val binding: ItemLockedmusicBinding) : RecyclerView.ViewHolder(binding.root){

        fun bind(song:Song){
            binding.itemLockedmusicTitleTv.text = song.title
            binding.itemLockedmusicArtistTv.text = song.artist
            binding.itemLockedmusicCoverImgIv.setImageResource(song.coverImg!!)
        }
    }
}


