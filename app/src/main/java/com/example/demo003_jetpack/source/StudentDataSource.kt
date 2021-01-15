package com.example.demo003_jetpack.source


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.demo003_jetpack.bean.StudentBean
import java.util.*
import java.util.concurrent.Executor

/**
 *author: Luuuzi
 *Date: 2021-01-15
 *description: DataSource负责加载第一页以及后面每一页数据
 */
open class StudentDataSource(var retryExecutor: Executor) :
    ItemKeyedDataSource<String, StudentBean>() {
    private var retry: (() -> Any)? = null
    private var startPosition: Int = 0
    val netWorkState by lazy {
        MutableLiveData<Resource<String>>()
    }
    val initialLoad by lazy {
        MutableLiveData<Resource<String>>()
    }

    /**
     * 接收初始加载的数据，在这里需要将获取到的数据通过LoadInitialCallback的onResult进行回调，用于出始化PagedList，并对加载的项目进行计数
     */
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<StudentBean>
    ) {
        Log.i("aaa", "loadInitial->mSkip:$startPosition,count:${params.requestedLoadSize}")
        netWorkState.postValue(Resource.loading(null))
        initialLoad.postValue(Resource.loading(null))
        //模拟耗时操作
        val loadData = loadData(startPosition, params.requestedLoadSize)
        retry=null
        netWorkState.postValue(Resource.success(null))
        callback.onResult(loadData)
        startPosition=loadData.size
    }

    /**
     * 接收加载的数据
     */
    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<StudentBean>) {
        Log.i("aaa","loadAfter")
    }

    /**
     * 加载之前
     */
    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<StudentBean>) {

    }

    override fun getKey(item: StudentBean): String {
        return item.id
    }

    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            retryExecutor.execute(it.invoke() as Runnable?)
        }
    }
    /**
     * 模拟耗时操作，假设这里需要做一些后台线程的数据加载任务。
     */
    private fun loadData(startPosition: Int, limit: Int): List<StudentBean> {
        val list = ArrayList<StudentBean>()
        for (i in 0 until limit) {
            val position = startPosition + i
            val data = StudentBean(position.toString(), "学生:$position")
            list.add(data)
        }
        return list
    }
}