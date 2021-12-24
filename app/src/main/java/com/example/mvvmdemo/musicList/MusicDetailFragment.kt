package com.example.mvvmdemo.musicList
import com.example.mvvmdemo.base.BaseFragment

class MusicDetailFragment:BaseFragment() {
    private val musicPresenter by lazy {
        MusicPresenter()
    }

    init {
        lifeProvider.addLifecycleListener(musicPresenter)
    }
}