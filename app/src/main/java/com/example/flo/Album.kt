package com.example.flo

//album의 data를 저장하는 data class
data class Album(
    var title: String? = "",
    var artist : String? = "",
    var coverImg : Int? = null
    //var song : ArrayList<Song>? = null
    //album의 수록곡을 저장하는 list (optional)
)