package com.example.mvvmdemo

import kotlin.random.Random

class UserModel {

    companion object{
        const val STATE_LOGIN_LOADING = 0
        const val STATE_LOGIN_SUCCESS = 1
        const val STATE_LOGIN_FAILED = 2
    }

    private val api by lazy {
        API()
    }

    private val random = Random

    /**
     * 登录操作
     */
    fun doLogin(account: String, passWord: String,block: (Int) -> Unit) {
        block.invoke(STATE_LOGIN_LOADING)
        //开始使用API登录

        //有结果，此操作为耗时操作
        Thread.sleep(100)
        val randomValue = random.nextInt(2)
        if (randomValue == 0) {
            block.invoke(STATE_LOGIN_SUCCESS)
        } else {
            block.invoke(STATE_LOGIN_FAILED)
        }
    }

    fun checkUserState(account:String,block:(Int) -> Unit){
        //0表示该账号已经注册
        //1表示未被注册
        block.invoke(random.nextInt(2))
    }

    interface OnDoLoginStateChange {

        fun onLoading()

        fun onLoadingSuccess()

        fun onLoadingFailed()
    }
}