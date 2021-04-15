package com.example.demo003_jetpack.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

/**
 * @author: Luuuzi
 * @Date: 2021-04-12
 * @description:2、自定义观察者lifecycleObserver
 */
class MyLifecycleObserver(var lifecycle: Lifecycle, var callback: CallBack) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public fun connectOnCreate() {
        callback.update("connectOnCreate()")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public fun connectOnResume() {
//        callback.update("connectOnResume()")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public fun connectOnStart(){
        callback.update("开始")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public fun connectOnPause(){
        callback.update("暂停")
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public fun connectOnDestory() {
        callback.update("connectOnDestory()")
    }
}