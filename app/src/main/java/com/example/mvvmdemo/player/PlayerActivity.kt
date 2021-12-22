package com.example.mvvmdemo.player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mvvmdemo.R
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity(), IPlayerCallback {

    private val TAG = "PlayerActivity"

    //view层持有presenter层
    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        playerPresenter.registerCallback(this)

        initListener()
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
        if (playerPresenter!=null) {
            playerPresenter.unregisterCallback(this)
        }
    }

    override fun onTitleChange(title: String) {
        songTitle.text = title
    }

    override fun onProgressChange(current: Int) {

    }

    override fun onPlaying() {
        //播放中显示暂停
        playOrPauseBtn.text = "暂停"
    }

    override fun onPlayerPause() {
        //暂停中显示播放
        playOrPauseBtn.text = "播放"
    }

    override fun onCoverChange(cover: String) {
        Log.d(TAG,"onCoverChange " + cover)
    }
}