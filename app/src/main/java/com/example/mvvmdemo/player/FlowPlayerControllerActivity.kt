package com.example.mvvmdemo.player

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import kotlinx.android.synthetic.main.activity_flow_player.*

class FlowPlayerControllerActivity : BaseActivity() {

    /**
     *当多个地方用到presenter时，可能存在多个无用的接口方法
     */

    private val playerPresenter by lazy {
        PlayerPresenter.instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flow_player)

        //playerPresenter.registerCallback(this)
        initListener()
        initDataListener()
    }

    private fun initDataListener() {
        playerPresenter.currentPlayState.addListener(this) {
            if (it == PlayerPresenter.PlayState.PLAYING) {
                playOrPauseBtn.text = "暂停"
            }else{
                playOrPauseBtn.text = "播放"
            }
        }
    }

    private fun initListener() {
        playOrPauseBtn.setOnClickListener {
            playerPresenter.doPlayOrPause()
        }
    }
}