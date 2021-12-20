package com.example.mvvmdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class LoginActivity : AppCompatActivity(),
            LoginController.OnCheckUserStateResultCallBack,
            LoginController.OnDoLoginStateChange {

    private val TAG = "MainActivity"

    private val loginController by lazy {
        LoginController()
    }

    private var isUserNameCanBeUse = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "onCreate...")

        initListener()
    }

    private fun initListener() {
        loginBtn.setOnClickListener {
            //进行登录
            toLogin()
        }

        //监听内容变化
        accountInputBox.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //检查当前账号
                loginController.checkUserNameState(s.toString(),this@LoginActivity)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    /**
     * 处理登录逻辑
     */
    private fun toLogin() {
        //做登录的逻辑
        val account = accountInputBox.text.toString()
        val passWord = passWordInputBox.text.toString()

        //检查账号格式
        if (TextUtils.isEmpty(passWord)) {
            //TODO：提示密码有问题
            return
        }
        //密码加盐
        if(!isUserNameCanBeUse){
            //提示用户当前用户名已经被注册了
            return
        }

        loginController.doLogin(account,passWord,this)

    }

    override fun onLoading() {
        loginTipsText.text = "登陆中..."
    }

    override fun onLoadingSuccess() {
        loginTipsText.text = "登陆成功..."
    }

    override fun onLoadingFailed() {
        loginTipsText.text = "登陆失败..."
    }

    override fun onNotExit() {
        //TODO:用户名可用
    }

    override fun onExit() {
        //TODO:用户名不可用
        loginTipsText.text = "用户名已被注册，不可用"
    }
}