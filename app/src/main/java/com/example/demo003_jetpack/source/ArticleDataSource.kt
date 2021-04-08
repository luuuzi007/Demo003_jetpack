package com.example.demo003_jetpack.source

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.demo003_jetpack.model.ArticleBean
import com.example.demo003_jetpack.model.DataX
import com.example.demo003_jetpack.net.Api
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:
 */
class ArticleDataSource : PageKeyedDataSource<Int, DataX>() {

    companion object{
        var page=0
    }
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataX>
    ) {

        val articleList = getArticleList(page++)

        articleList.enqueue(object : Callback<ArticleBean> {
            override fun onFailure(call: Call<ArticleBean>, t: Throwable) {
                Log.i("aaa","第一次加载失败:${t.message}")
            }

            override fun onResponse(call: Call<ArticleBean>, response: Response<ArticleBean>) {
                val body = response.body()
                val datas = body?.data?.datas
                Log.i("aaa","第一次加载成功:${datas?.size}")
                callback.onResult(datas!!, page, 10)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        val articleList = getArticleList(page++)
        articleList.enqueue(object : Callback<ArticleBean> {
            override fun onFailure(call: Call<ArticleBean>, t: Throwable) {
                Log.i("aaa","加载更多失败:${t.message}")
            }

            override fun onResponse(call: Call<ArticleBean>, response: Response<ArticleBean>) {
                val body = response.body()
                val datas = body?.data?.datas
                Log.i("aaa","加载更多成功:${datas?.size}")
                callback.onResult(datas!!, page)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {

    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.wanandroid.com/")
            .build()
    }
    private val mApi by lazy {
        retrofit.create(Api::class.java)
    }

    //获取文章列表
    fun getArticleList(page: Int): Call<ArticleBean> {
        return mApi.getArticleList(page)
    }
}