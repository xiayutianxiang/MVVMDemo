package com.example.mvvmdemo.musicList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mvvmdemo.R
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : AppCompatActivity() {

    private val musicPresenter by lazy {
        MusicPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)

        initDataListener()
        initViewListener()

    }

    /**
     * 监听数据变化
     */
    private fun initDataListener() {
        musicPresenter.musicList.addListener {
            println(Thread.currentThread().name)

            //数据变化
            println("加载状态：${it?.size}")
        }

        musicPresenter.loadState.addListener {
            //数据状态变化
            println("加载状态：$it")
        }
    }

    private fun initViewListener() {
        getMusicBtn.setOnClickListener {
            musicPresenter.getMusic()
        }
    }
}