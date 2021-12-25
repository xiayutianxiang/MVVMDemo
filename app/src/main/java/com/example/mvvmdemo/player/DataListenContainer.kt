package com.example.mvvmdemo.player

import android.os.Looper
import com.example.mvvmdemo.App
import com.example.mvvmdemo.lifecycle.*

/**
 * 数据容器，可以监听数据的变化
 */
class DataListenContainer<T> {

    private val blocks = arrayListOf<(T?) -> Unit>()
    private val viewLifecycleProviders = hashMapOf<(T?) -> Unit,LifecycleProvider >()
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
        val viewModelProvider: LifecycleProvider? = viewLifecycleProviders[it]
        if (viewModelProvider != null && viewModelProvider.isAtLeast(LifeState.START)) {
            it.invoke(value)
        }
    }

    /**
     * 可能有多个view进行监听
     * 所有owner-block需要管理
     */
    fun addListener(owner: ILifecycleOwner, valueObserver: (T?) -> Unit) {
        //viewLifecycleProviders.put(block,owner.getLifecycleProvider())
        val lifecycleProvider:LifecycleProvider = owner.getLifecycleProvider()
        viewLifecycleProviders[valueObserver] =lifecycleProvider

        val  observerWrapper = ValueObserverWrapper(valueObserver)
        lifecycleProvider.addLifecycleListener(observerWrapper)

        //当view destroy
        if (!blocks.contains(valueObserver)) {
            blocks.add(valueObserver)
        }
    }

    inner class ValueObserverWrapper(private val valueObserver:(T?)->Unit):
        AbsLifecycle() {

        override fun onViewLifeStateChange(state: LifeState) {
            //当监听到view生命周期为destroy时，就把lifecycleProvider从集合中删除、
            if (state===LifeState.DESTROY) {
                viewLifecycleProviders.remove(valueObserver)
            }
        }

    }
}