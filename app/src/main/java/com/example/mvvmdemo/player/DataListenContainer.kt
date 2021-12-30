package com.example.mvvmdemo.player

import android.os.Looper
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.example.mvvmdemo.App

/**
 * 数据容器，可以监听数据的变化
 */
class DataListenContainer<T> {

    private val blocks = arrayListOf<(T?) -> Unit>()
    private val viewLifecycleProviders = hashMapOf<(T?) -> Unit,Lifecycle >()
    var value: T? = null

        //数据变化时候，就通知更新
        set(value: T?) {
            //判断当前线程是不是主线程
            //如果是，直接执行，否则切换到主线程
            if (Looper.getMainLooper().thread== Thread.currentThread()) {
                //判断对应view的生命周期

                blocks.forEach {
                    dispatchValue(it, value)
                }
            }else{
                App.handler.post{
                    blocks.forEach { dispatchValue(it,value) }
                }
            }
        }

    private fun dispatchValue(it: (T?) -> Unit, value: T?) {
        val lifecycle: Lifecycle? = viewLifecycleProviders[it]
        if (lifecycle != null && lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            println("更新ui")
            it.invoke(value)
        }
    }

    /**
     * 可能有多个view进行监听
     * 所有owner-block需要管理
     */
    fun addListener(owner: LifecycleOwner, valueObserver: (T?) -> Unit) {
        //viewLifecycleProviders.put(block,owner.getLifecycleProvider())
        val lifecycle: Lifecycle = owner.lifecycle

        viewLifecycleProviders[valueObserver] = lifecycle

        val  observerWrapper = ValueObserverWrapper(valueObserver)
        lifecycle.addObserver(observerWrapper)

        //当view destroy
        if (!blocks.contains(valueObserver)) {
            blocks.add(valueObserver)
        }
    }

    inner class ValueObserverWrapper(private val valueObserver:(T?)->Unit):
        LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun removeValueObserver(){
            println("removeValueObserver...")
            viewLifecycleProviders.remove(valueObserver)
        }
    }
}