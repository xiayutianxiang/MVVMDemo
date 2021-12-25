package com.example.mvvmdemo.musicList

import android.util.Log
import com.example.mvvmdemo.lifecycle.AbsLifecycle
import com.example.mvvmdemo.lifecycle.ILifecycle
import com.example.mvvmdemo.lifecycle.ILifecycleOwner
import com.example.mvvmdemo.lifecycle.LifeState
import com.example.mvvmdemo.player.DataListenContainer
import com.example.mvvmdemo.player.domain.Music

class MusicPresenter(owner: ILifecycleOwner){

    private val viewLifeImpl by lazy {
        ViewLifeImpl()
    }

    init {
        owner.getLifecycleProvider().addLifecycleListener(viewLifeImpl)
    }

    private val TAG = "MusicPresenter"
    private val musicModel by lazy {
        MusicModel()
    }

    enum class MusicLoadState {
        LOADING, EMPTY, SUCCESS, ERROR
    }

    val musicList = DataListenContainer<List<Music>>()
    val loadState = DataListenContainer<MusicLoadState>()
    private val page = 1
    private val size = 20

    fun getMusic() {
        loadState.value = MusicLoadState.LOADING
        //从model层获取音乐数据
        musicModel.loadMusicByPage(page, size, object : MusicModel.OnMusicLoadResult {
            override fun onSuccess(result: List<Music>) {
                musicList.value = result
                if (result.isEmpty()) {
                    loadState.value = MusicLoadState.EMPTY
                } else {
                    loadState.value = MusicLoadState.SUCCESS
                }
            }

            override fun onError(msg: String, code: Int) {
                loadState.value = MusicLoadState.ERROR
                print("error："+msg)
            }
        })
    }
    inner class ViewLifeImpl:AbsLifecycle() {

        override fun onCreate() {
            //监听GPS信号变化
            Log.d(TAG, "开始信号监听")
        }

        override fun onStart() {

        }

        override fun onResume() {

        }

        override fun onPause() {

        }

        override fun onStop() {
            //停止bianhua
            Log.d(TAG, "停止信号监听")
        }

        override fun onDestroy() {

        }

        override fun onViewLifeStateChange(state: LifeState) {
            TODO("Not yet implemented")
        }
    }
}