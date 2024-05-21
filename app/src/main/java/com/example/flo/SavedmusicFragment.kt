package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.databinding.FragmentSavedmusicBinding

//recyclerview 이용
class SavedmusicFragment : Fragment() {

    lateinit var binding : FragmentSavedmusicBinding
    private val lockedMusicDatas = ArrayList<Song>() //song 데이터를 담기 위한 배열

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedmusicBinding.inflate(inflater, container, false)

        //더미 데이터
        lockedMusicDatas.apply{
            add(Song("Surrender", "Alexis King", 0, 0, false, "sample_music_1", R.drawable.img_home_album))
            add(Song("Lilac", "IU", 0, 0, false, "sample_music_1", R.drawable.img_album_exp2))
            add(Song("Butter", "BTS", 0, 0, false, "sample_music_1", R.drawable.img_album_exp))
            add(Song("Closer", "The ChainSmokers", 0, 0, false, "sample_music_1", R.drawable.img_album_exp3))
            add(Song("Lost stars", "Adam Levine", 0, 0, false, "sample_music_1", R.drawable.img_album_exp4))
            add(Song("Surrender", "Alexis King", 0, 0, false, "sample_music_1", R.drawable.img_home_album))
            add(Song("Lilac", "IU", 0, 0, false, "sample_music_1", R.drawable.img_album_exp2))
            add(Song("Butter", "BTS", 0, 0, false, "sample_music_1", R.drawable.img_album_exp))
            add(Song("Closer", "The ChainSmokers", 0, 0, false, "sample_music_1", R.drawable.img_album_exp3))
            add(Song("Lost stars", "Adam Levine", 0, 0, false, "sample_music_1", R.drawable.img_album_exp4))
        }

        val songRVAdapter  = SongRVAdapter(lockedMusicDatas)
        binding.savedmusicMusicListRv.adapter = songRVAdapter
        binding.savedmusicMusicListRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        songRVAdapter.setMyItemClickListener(object:SongRVAdapter.MyItemClickListener{
            override fun onMoreBtnClick(idx: Int) {
                songRVAdapter.removeItem(idx)
            }
        })

        return binding.root
    }
}