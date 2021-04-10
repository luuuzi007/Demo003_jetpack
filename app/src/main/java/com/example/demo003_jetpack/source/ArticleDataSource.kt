package com.example.demo003_jetpack.source

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.demo003_jetpack.model.DataX
import com.example.demo003_jetpack.net.RetrofitNetwork
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:
 */
class ArticleDataSource : PageKeyedDataSource<Int, DataX>() {

    companion object {
        var page = 0
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataX>
    ) {

//        val articleList = getArticleList(page++)
//
//        articleList.enqueue(object : Callback<ArticleBean> {
//            override fun onFailure(call: Call<ArticleBean>, t: Throwable) {
//                Log.i("aaa","第一次加载失败:${t.message}")
//            }
//
//            override fun onResponse(call: Call<ArticleBean>, response: Response<ArticleBean>) {
//                val body = response.body()
//                val datas = body?.data?.datas
//                Log.i("aaa","第一次加载成功:${datas?.size}")
//                callback.onResult(datas!!, page, 10)
//            }
//        })
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.IO) {
            page = 1
            val articleBean = RetrofitNetwork.getArticleList(page)
            if (articleBean?.errorCode==0) {
                val datas = articleBean?.data?.datas
                Log.i("aaa", "第一次加载.size:${datas.size}")
                /**
                 * 第一个参数会交给PagedList，
                 * 第二个参数会交给，
                 * 第三个参数会传给调用loadAfter方法的params的key属性,只传一次
                 */
                callback.onResult(datas, null, page + 1)//第一个参数会交给PagedList
            }else{
                //错误
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        Log.i("aaa", "params.key:${params.key}")
        //这个值和PagedList.Config.Builder().setPageSize(11)一样
        Log.i("aaa", "params.requestedLoadSize:${params.requestedLoadSize}")
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch(Dispatchers.IO) {
            val articleBean = RetrofitNetwork.getArticleList(params.key)
            if (articleBean?.errorCode==0){
                val datas = articleBean?.data?.datas
                Log.i("aaa", "加载更多.size:${datas.size}")
                /**
                 * 第一个参数会交给PagedList，第二个参数会传给下一次调用loadAfter方法的params的key属性
                 */
                callback.onResult(datas, params.key + 1)
            }else{
                //错误
            }

        }
    }
    //没啥用
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataX>) {
        Log.i("aaa","上一页：${params.key}")
    }
}