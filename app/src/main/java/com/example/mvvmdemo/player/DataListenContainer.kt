package com.example.mvvmdemo.player

import android.os.Looper
import com.example.mvvmdemo.App

/**
 * 数据容器，可以监听数据的变化
 */
class DataListenContainer<T> {

    private val blocks = arrayListOf<(T?) -> Unit>()

    var value: T? = null

        //数据变化时候，就通知更新
        set(value: T?) {
            //判断当前线程是不是主线程
            //如果是，直接执行，否则切换到主线程
            if (Looper.getMainLooper().thread== Thread.currentThread()) {
                blocks.forEach { it.invoke(value) }
            }else{
                App.handler.post{
                    blocks.forEach { it.invoke(value) }
                }
            }
        }

    fun addListener(block: (T?) -> Unit) {
        if (!blocks.contains(block)) {
            blocks.add(block)
        }
    }
}