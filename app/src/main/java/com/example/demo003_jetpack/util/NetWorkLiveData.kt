package com.example.demo003_databing.util

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import androidx.lifecycle.LiveData

/**
 * @author: Luuuzi
 * @date: 2020-12-24
 * @description:自定义livedata
 */
class NetWorkLiveData(var context: Context) : LiveData<NetworkInfo>() {
    private var mContext: Context = context.applicationContext
    private var mNetWorkReceiver: NetWorkReceiver = NetWorkReceiver()
    private var mIntentFilter: IntentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)

    companion object {
        private var mNetWorkLiveData: NetWorkLiveData? = null
        fun getInstance(context: Context): NetWorkLiveData? {
            if (mNetWorkLiveData == null) {
                mNetWorkLiveData = NetWorkLiveData(context)
            }
            return mNetWorkLiveData
        }
    }
    //当处于active状态的观察者(activity或Fragment)数量从0变成1时回调
    override fun onActive() {
        super.onActive()
        Log.i("aaa","onActive")
        //注册广播，监听网络变化
        mContext.registerReceiver(mNetWorkReceiver, mIntentFilter)
    }
    //当处于active状态的观察者(activity或Fragment)数量变为0时回调
    override fun onInactive() {
        super.onInactive()
        Log.i("aaa","onInactive")
        //取消注册广播：取消监听
        mContext.unregisterReceiver(mNetWorkReceiver)
    }

    class NetWorkReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val systemService =
                context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = systemService.activeNetworkInfo
            Log.i("aaa","广播回调：$activeNetworkInfo")
            getInstance(context)?.value = activeNetworkInfo
        }

    }
}