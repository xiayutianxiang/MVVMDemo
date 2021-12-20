package com.example.mvvmdemo.player


/**
 * view层的接口，和ui改变有关
 */

interface IPlayerCallback {

    fun onTitleChange(title:String)

    fun onProgressChange(current:Int)

    fun onPlaying()

    fun onPlayerPause()

    fun onCoverChange(cover:String)
}