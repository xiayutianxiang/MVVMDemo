package com.example.mvvmdemo.player

import com.example.mvvmdemo.player.domain.Music


/**
 * 播放音乐
 * 暂停音乐
 * 上一首
 * 下一首
 *
 * =====播放状态改变
 * 通知ui改变
 * 相关数据
 *  当前歌曲、状态
 */
class PlayerPresenter private constructor() {

    private val playerModel by lazy {
        PlayModel()
    }

    private val player by lazy {
        MusicPlayer()
    }

    var currentMusic = DataListenContainer<Music>()

    companion object {
        val instance by lazy {
            PlayerPresenter()
        }
    }

    //private val callbackList = arrayListOf<IPlayerCallback>()

    enum class PlayState {
        NONE, PLAYING, PAUSE, LOADING
    }

    var currentPlayState = DataListenContainer<PlayState>()

    //view要实现IPlayerCallback中方法，在这里注册（也可以不注册），IPlayerCallback可能会在多个地方调用
    //所以用一个集合来保存起来
    /*fun registerCallback(callback: IPlayerCallback) {
        if (!callbackList.contains(callback)) {
            callbackList.add(callback)
        }
    }

    fun unregisterCallback(callback: IPlayerCallback) {
        callbackList.remove(callback)
    }*/

    /**
     * 根据状态控制播放暂停
     */
    fun doPlayOrPause() {
        if (currentMusic==null) {
            //去获取一首歌曲
            currentMusic.value= playerModel.getMusicById("二胡")
        }

        player.play(currentMusic.value)
        /*dispatchTitleChange("当前播放的歌曲标题...")
        dispatchCoverChange("当前播放的歌曲封面...")*/
        if (currentPlayState.value != PlayState.PLAYING) {
            //开始播放音乐
            //dispatchPlayingState()
            currentPlayState.value = PlayState.PLAYING
        } else {
            //暂停播放
           // dispatchPauseState()
            currentPlayState.value = PlayState.PAUSE
        }
    }

    /*private fun dispatchPauseState() {
        callbackList.forEach {
            it.onPlayerPause()
        }
    }

    private fun dispatchPlayingState() {
        callbackList.forEach {
            it.onPlaying()
        }
    }*/

    //下一首
    fun playNext() {
        currentMusic.value = playerModel.getMusicById("下一首，笛子")
        //TODO:播放下一首
        //1.拿到下一首歌曲 --->更新ui，标题、封面
       /* dispatchTitleChange("切换到下一首")
        dispatchCoverChange("切换到下一首,封面改变")*/
        //2.设置给播放器
        //3.等待播放回调通知
        currentPlayState.value = PlayState.PLAYING
    }

    /*private fun dispatchCoverChange(cover: String) {
        callbackList.forEach {
            it.onCoverChange(cover)
        }
    }

    private fun dispatchTitleChange(title: String) {
        callbackList.forEach {
            it.onTitleChange(title)
        }
    }*/

    //上一首
    fun playPre() {
        currentMusic.value = playerModel.getMusicById("上一首：琴")
        //1.拿到上一首歌曲 --->更新ui，标题、封面
        /*dispatchTitleChange("切换到上一首")
        dispatchCoverChange("切换到上一首,封面改变")*/
        //2.设置给播放器
        //3.等待播放回调通知
        currentPlayState.value = PlayState.PLAYING
    }
}