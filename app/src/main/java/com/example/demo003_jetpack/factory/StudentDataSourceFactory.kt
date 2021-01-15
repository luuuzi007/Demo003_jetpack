package com.example.demo003_jetpack.factory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.demo003_jetpack.bean.StudentBean
import com.example.demo003_jetpack.source.StudentDataSource
import java.net.HttpRetryException
import java.util.concurrent.Executor

/**
 *author: Luuuzi
 *Date: 2021-01-14
 *description:简单的数据工厂，它提供了一种观察上次创建的数据源的方式，这使得我们能够将其网络请求状态等返回到UI界面
 */
class StudentDataSourceFactory(var retryExecutor: Executor): DataSource.Factory<String,StudentBean>() {
    val sourceLiveData= MutableLiveData<StudentDataSource>()
    override fun create(): DataSource<String, StudentBean> {
        val source = StudentDataSource(retryExecutor)
        sourceLiveData.postValue(source)
        return source
    }
}