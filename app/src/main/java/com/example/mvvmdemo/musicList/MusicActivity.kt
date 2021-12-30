package com.example.mvvmdemo.musicList

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import com.example.mvvmdemo.R
import com.example.mvvmdemo.base.BaseActivity
import com.example.mvvmdemo.player.domain.Music
import kotlinx.android.synthetic.main.activity_music.*

class MusicActivity : BaseActivity(){

    private val TAG:String = "MusicActivity"

    private lateinit var mForeverObserver : ForeverObserver;

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music)
        initDataListener()
        initViewListener()
        val cur = Lifecycle.State.DESTROYED > Lifecycle.State.STARTED
        Log.d(TAG,"cur：$cur")
    }

    inner class ForeverObserver:Observer<List<Music>>{
        override fun onChanged(result: List<Music>?) {
            Log.d(TAG,"ForeverObserver onChanged ${result?.size}")
        }
    }

    /**
     * 监听数据变化
     */
    private fun initDataListener() {

        /*musicPresenter.liveMusicList.observe(this, Observer {
            Log.d(TAG,"live data中数据更新了")
        })*/

        mForeverObserver = ForeverObserver()
        musicPresenter.liveMusicList.observeForever(mForeverObserver)

        musicPresenter.musicList.addListener(this) {
            println(Thread.currentThread().name)

            //数据变化
            println("加载状态：${it?.size}")
        }

        musicPresenter.loadState.addListener(this) {
            //数据状态变化
            println("加载状态：$it")
        }
    }

    private fun initViewListener() {
        getMusicBtn.setOnClickListener {
            musicPresenter.getMusic()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        musicPresenter.liveMusicList.removeObserver(mForeverObserver)
    }
}