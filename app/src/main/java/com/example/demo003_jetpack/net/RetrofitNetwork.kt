package com.example.demo003_jetpack.net

import com.example.demo003_jetpack.model.ArticleBean
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:获取数据
 */
object RetrofitNetwork {
    //1、创建retrofit
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.wanandroid.com/")
            .build()
    }

    //2、创建mapi
    private val mApi by lazy {
        retrofit.create(Api::class.java)
    }

    //获取文章列表
    suspend fun getArticleList(page: Int): ArticleBean? {
        try {//捕获异常，业务逻辑自己处理
            return mApi.getArticleList(page).await()
        }catch (e:Exception){
            e.printStackTrace()
        }
        return null
    }

    //3、为call增加扩展函数await,实现网络请求
    private suspend inline fun <T> Call<T>.await(): T {
        return suspendCoroutine<T> {
            this.enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    return it.resumeWithException(RuntimeException("获取数据失败"))
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) {
                        return it.resume(body)
                    } else {
                        return it.resumeWithException(RuntimeException("获取数据为空"))
                    }
                }
            })
        }
    }
}