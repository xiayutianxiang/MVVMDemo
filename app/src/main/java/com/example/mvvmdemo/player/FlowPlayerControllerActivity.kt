package com.example.mvvmdemo.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmdemo.R
import kotlinx.android.synthetic.main.activity_flow_player.*

class FlowPlayerControllerActivity : AppCompatActivity(), IPlayerCallback {

    /**
     *当多个地方用到presenter时，可能存在多个无用的接口方法
     */

    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_player)

        playerPresenter.registerCallback(this)
        initListener()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerPresenter.unregisterCallback(this)
    }
    private fun initListener() {
        playOrPauseBtn.setOnClickListener {
            playerPresenter.doPlayOrPause()
        }
    }

    override fun onTitleChange(title: String) {
        TODO("Not yet implemented")
    }

    override fun onProgressChange(current: Int) {
        TODO("Not yet implemented")
    }

    override fun onPlaying() {
        playOrPauseBtn.text = "暂停"
    }

    override fun onPlayerPause() {
        playOrPauseBtn.text = "播放"
    }

    override fun onCoverChange(cover: String) {
        TODO("Not yet implemented")
    }
}