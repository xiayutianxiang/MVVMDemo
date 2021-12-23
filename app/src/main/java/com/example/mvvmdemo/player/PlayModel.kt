package com.example.mvvmdemo.player

import com.example.mvvmdemo.player.domain.Music

class PlayModel {
    fun getMusicById(id: String) :Music{
        return Music("歌曲名...$id",
            "xxx.png",
            "www.baidu.com")
    }
}