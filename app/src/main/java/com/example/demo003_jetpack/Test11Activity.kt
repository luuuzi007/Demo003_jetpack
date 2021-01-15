package com.example.demo003_jetpack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import java.lang.System.*

/**
 * @author: Luuuzi
 * @date: 2020-12-31
 * @description:协程
 */
class Test11Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initData()
//        initData2()
//        initData3()
        initData4()
    }

    /**
     * runBlocking:T
     */
    private fun initData() {
        Log.i("aaa", "主线程id:${Thread.currentThread().id}")
        runBlocking {
            //执行给定的功能[action]指定的[次数]。

            repeat(6) {
                Log.i("aaa", "线程执行$it ,线程id：${Thread.currentThread().id}")
                //延迟1000毫秒
                delay(1000)
            }
        }
        Log.i("aaa", "协程执行结束")
    }

    /**
     * launch:Job
     */
    private fun initData2() {
        Log.i("aaa", "initData2主线程id:${Thread.currentThread().id}")
        val job = GlobalScope.launch {
            delay(6000)
            Log.i("aaa", "协程执行结束 ,线程id：${Thread.currentThread().id}")
        }
        Log.i("aaa", "主线程执行结束")
    }

    /**
     *suspend函数
     */
    private fun initData3() {
        GlobalScope.launch {
            getToken()
            getUserInfo()
        }
        repeat(9) {
            Log.i("aaa", "协程执行结束 ,线程id：${Thread.currentThread().id}")
        }
    }

    private suspend fun getToken() {
        Log.i("aaa", "gettoken")
    }

    private fun getUserInfo() {
        Log.i("aaa", "getUserInfo")
    }

    /**
     * async
     */
    private fun initData4() {
        val firstTime = currentTimeMillis()
        Log.i("aaa","initData4，线程id：${Thread.currentThread().id}")
        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async {
                getResult2()
            }
            val result = result1.await() + result2.await()
            Log.i("aaa","result ->$result")
            val l = currentTimeMillis() - firstTime
            Log.i("aaa", "时间差 = $l")
        }
        Log.i("aaa","主线程结束,线程id：${Thread.currentThread().id}")
    }

    private suspend fun getResult1(): Int {
        delay(3000)
        return 1
    }

    private suspend fun getResult2(): Int {
        delay(4000)
        return 2
    }
}