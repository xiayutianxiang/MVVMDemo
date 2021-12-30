package com.example.mvvmdemo.musicList

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.example.mvvmdemo.player.DataListenContainer
import com.example.mvvmdemo.player.domain.Music

class MusicPresenter(owner: LifecycleOwner){

    private val viewLifeImpl by lazy {
        ViewLifeImpl()
    }

    init {
        owner.lifecycle.addObserver(viewLifeImpl)
    }

    private val TAG = "MusicPresenter"
    private val musicModel by lazy {
        MusicModel()
    }

    //MutableLiveData继承livedata（抽象类）
    val liveMusicList = MutableLiveData<List<Music>>()

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
                liveMusicList.postValue(result)

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
    inner class ViewLifeImpl:LifecycleEventObserver {

        /*override fun onCreate() {
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
            //停止变化
            Log.d(TAG, "停止信号监听")
        }

        override fun onDestroy() {

        }

        override fun onViewLifeStateChange(state: LifeState) {
            TODO("Not yet implemented")
        }*/

        override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
            when(event){
                Lifecycle.Event.ON_START ->{
                    //监听GPS信号变化
                    Log.d(TAG, "开始信号监听")
                }
                Lifecycle.Event.ON_STOP ->{
                    //停止变化
                    Log.d(TAG, "停止信号监听")
                }else -> {

                }
            }
        }
    }
}