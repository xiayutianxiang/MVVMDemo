package com.example.mvvmdemo.musicList

import com.example.mvvmdemo.base.BaseFragment

class MusicListFragment : BaseFragment() {

    private val musicPresenter by lazy {
        MusicPresenter(this)
    }

}