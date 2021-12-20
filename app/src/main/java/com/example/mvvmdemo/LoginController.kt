package com.example.mvvmdemo

import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_FAILED
import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_LOADING
import com.example.mvvmdemo.UserModel.Companion.STATE_LOGIN_SUCCESS

class LoginController {

    private val userModel by lazy {
        UserModel()
    }

    fun checkUserNameState(account: String, callback: OnCheckUserStateResultCallBack) {
        userModel.checkUserState(account) {
            when (it) {
                0 -> {
                    callback.onExit()
                }
                1 -> {
                    callback.onNotExit()
                }
            }
        }
    }

    interface OnCheckUserStateResultCallBack {
        fun onNotExit()

        fun onExit()
    }

    fun doLogin(userName: String, passWord: String, callback: OnDoLoginStateChange) {
        userModel.doLogin(userName,passWord){
            when(it){
                STATE_LOGIN_LOADING ->{
                    callback.onLoading()
                }
                STATE_LOGIN_SUCCESS->{
                    callback.onLoadingSuccess()
                }
                STATE_LOGIN_FAILED->{
                    callback.onLoadingFailed()
                }
            }
        }
    }

    interface OnDoLoginStateChange {

        fun onLoading()

        fun onLoadingSuccess()

        fun onLoadingFailed()
    }
}