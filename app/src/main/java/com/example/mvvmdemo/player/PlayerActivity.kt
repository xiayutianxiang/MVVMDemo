package com.example.mvvmdemo.player

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.musicList.MusicPresenter
import kotlinx.android.synthetic.main.activity_player.*
import java.util.*

class PlayerActivity : BaseActivity() {

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

    private val TAG = "PlayerActivity"

    //view层持有presenter层
    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        //playerPresenter.registerCallback(this)

        initListener()
        initDataListener()

    }

    class LivePlayerStateObserver : Observer<PlayerPresenter.PlayState>{
        private val TAG = "LivePlayerStateObserver"
        override fun onChanged(t: PlayerPresenter.PlayState?) {
            Log.d(TAG,"播放器界面。。。live data ---> 当前的状态")
        }
    }

    private val livePlayerStateObserver by lazy {
        LivePlayerStateObserver()
    }

    /**
     * 对数据进行监听
     */
    private fun initDataListener() {

        LivePlayerState.instances.observeForever(livePlayerStateObserver)

        playerPresenter.currentMusic.addListener(this) {
            //音乐内容发生变化
            songTitle.text = it?.name
            Log.d(TAG,"封面改变了...${it?.cover}")
        }
        playerPresenter.currentPlayState.addListener(this) {
            when(it){
                PlayerPresenter.PlayState.PAUSE->{
                    playOrPauseBtn.text = "播放"
                }
                PlayerPresenter.PlayState.PLAYING->{
                    playOrPauseBtn.text = "暂停"
                }
            }
        }
    }

    /**
     * 给控件设置相关事件
     */
    private fun initListener() {
        playOrPauseBtn.setOnClickListener {
            //调用presenter层的播放暂停
            playerPresenter.doPlayOrPause()
        }

        playNext.setOnClickListener {
            playerPresenter.playNext()
        }

        playPre.setOnClickListener {
            playerPresenter.playPre()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LivePlayerState.instances.removeObserver(livePlayerStateObserver)
    }
}