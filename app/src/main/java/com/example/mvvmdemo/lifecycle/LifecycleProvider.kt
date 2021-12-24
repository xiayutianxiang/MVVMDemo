package com.example.mvvmdemo.lifecycle


/**
 * 管理所注册进来的接口，这个接口就是Ilifecycle
 * 保存当前view的生命周期状态
 */

class LifecycleProvider {

    private var currentLifeState: LifeState? = null
    private val lifecycleList = arrayListOf<ILifecycle>()

    fun addLifecycleListener(lifecycle: ILifecycle) {
        if (!lifecycleList.contains(lifecycle)) {
            lifecycleList.add(lifecycle)
        }
    }

    fun removeLifecycleListener(lifecycle: ILifecycle) {
        lifecycleList.remove(lifecycle)
    }

    fun makeLifeState(state: LifeState) {
        currentLifeState = state
        when (state) {
            LifeState.CREATE -> {
                dispatchCreateState()
            }
            LifeState.START -> {
                dispatchStartState()
            }
            LifeState.RESUME -> {
                dispatchResumeState()
            }
            LifeState.PAUSE -> {
                dispatchPauseState()
            }
            LifeState.STOP -> {
                dispatchStopState()
            }
            LifeState.DESTROY -> {
                dispatchDestroyState()
            }
        }
    }

    private fun dispatchDestroyState() {

    }

    private fun dispatchStopState() {

    }

    private fun dispatchPauseState() {

    }

    private fun dispatchResumeState() {

    }

    private fun dispatchStartState() {

    }

    private fun dispatchCreateState() {
        lifecycleList.forEach {
            it.onCreate()
        }
    }
}