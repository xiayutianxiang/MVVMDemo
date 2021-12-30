package com.example.mvvmdemo.player

import androidx.lifecycle.LiveData

/**
 * private constructor()私有化
 */
class LivePlayerState private constructor() : LiveData<PlayerPresenter.PlayState>(){

    companion object{
        val instances by lazy {
            LivePlayerState()
        }
    }

    public override fun postValue(value: PlayerPresenter.PlayState?) {
        super.postValue(value)
    }
}