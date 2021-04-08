package com.example.demo003_jetpack.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.demo003_jetpack.factory.StudentDataFactory
import com.example.demo003_jetpack.model.StudentBean

/**
 *author: Luuuzi
 *Date: 2021-01-20
 *description:3、viewmodel 和处理数据和UI交互
 */
class StudentViewModel : ViewModel() {

    private var listLiveData: LiveData<PagedList<StudentBean>>
    //1、初始化
    init {
        val factory = StudentDataFactory()
        //配置第一次默认加载多少数据，之后每一次加载多少数据，如何加载 等等
        val build = PagedList.Config.Builder()
            .setPageSize(10)                  //配置分页加载的数量
            .setEnablePlaceholders(false)     ///设置控件占位
            .setInitialLoadSizeHint(30)       //首次加载的数量
            .setPrefetchDistance(5)     //距离底部还有多少条数据时开始预加载
            .build()
        //创建liveData对象。配置PageList，将Factory和属性关联起来
        listLiveData = LivePagedListBuilder<Int, StudentBean>(factory, build).build()
    }
    //2、返回数据
    fun getStudents(): LiveData<PagedList<StudentBean>> {
        return listLiveData
    }
}