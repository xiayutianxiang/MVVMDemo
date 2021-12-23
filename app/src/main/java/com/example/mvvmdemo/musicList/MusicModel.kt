package com.example.mvvmdemo.musicList

import com.example.mvvmdemo.player.domain.Music
import javax.security.auth.callback.Callback

class MusicModel {
    fun loadMusicByPage(page: Int, size: Int,callback: OnMusicLoadResult) {
        val result = arrayListOf<Music>()
        Thread{
            for (i in 0..30){
                result.add(Music(
                    "音乐名称$i",
                    "封面$i",
                    "URL$i"
                ))
            }
            //数据请求完成
            //通知更新
            callback.onSuccess(result)
        }.start()
    }

    interface OnMusicLoadResult {
        fun onSuccess(result: List<Music>)
        fun onError(msg: String, code: Int)
    }
}