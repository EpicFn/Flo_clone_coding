package com.example.flo

data class Song(
    val title : String = "",
    val artist : String = "",
    var second: Int = 0,
    var playTime : Int = 0,
    var isPlaying : Boolean = false
)
