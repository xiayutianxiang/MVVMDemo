package com.example.mvvmdemo.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.musicList.MusicPresenter
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : BaseActivity() {

    private val musicPresenter by lazy {
        MusicPresenter()
    }

    private val TAG = "PlayerActivity"

    //view层持有presenter层
    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    init {
        lifeProvider.addLifecycleListener(musicPresenter)
        lifeProvider.addLifecycleListener(playerPresenter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        //playerPresenter.registerCallback(this)

        initListener()
        initDataListener()

        musicPresenter.onCreate()
    }

    /**
     * 对数据进行监听
     */
    private fun initDataListener() {
        playerPresenter.currentMusic.addListener {
            //音乐内容发生变化
            songTitle.text = it?.name
            Log.d(TAG,"封面改变了...${it?.cover}")
        }
        playerPresenter.currentPlayState.addListener {
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
}