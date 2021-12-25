package com.example.mvvmdemo.lifecycle


/**
 * 管理所注册进来的接口，这个接口就是Ilifecycle
 * 保存当前view的生命周期状态
 */

class LifecycleProvider {

    private var currentLifeState: LifeState = LifeState.DESTROY
    private val lifecycleList = arrayListOf<AbsLifecycle>()

    fun addLifecycleListener(lifecycle: AbsLifecycle) {
        if (!lifecycleList.contains(lifecycle)) {
            lifecycleList.add(lifecycle)
        }
    }

    fun removeLifecycleListener(lifecycle: AbsLifecycle) {
        lifecycleList.remove(lifecycle)
    }

    fun makeLifeState(state: LifeState) {
        currentLifeState = state
        lifecycleList.forEach {
            it.onViewLifeStateChange(state)
        }

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
        lifecycleList.forEach {
            it.onDestroy()
        }
    }

    private fun dispatchStopState() {
        lifecycleList.forEach {
            it.onStop()
        }
    }

    private fun dispatchPauseState() {
        lifecycleList.forEach {
            it.onPause()
        }
    }

    private fun dispatchResumeState() {
        lifecycleList.forEach {
            it.onResume()
        }
    }

    private fun dispatchStartState() {
        lifecycleList.forEach {
            it.onStart()
        }
    }

    private fun dispatchCreateState() {
        lifecycleList.forEach {
            it.onCreate()
        }
    }

    fun isAtLeast(state: LifeState) : Boolean {
        println("current state $currentLifeState === $state")
        val isAsLeastState:Boolean = currentLifeState > state
        println("isAsLeastState ==> $isAsLeastState" )
        return currentLifeState > state

    }
}