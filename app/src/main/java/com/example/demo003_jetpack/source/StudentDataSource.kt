package com.example.demo003_jetpack.source


import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.demo003_jetpack.model.StudentBean

/**
 *author: Luuuzi
 *Date: 2021-01-15
 *description: 1、创建数据源，负责加载数据
 */
open class StudentDataSource : PositionalDataSource<StudentBean>() {
    //首次加载调用
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<StudentBean>
    ) {
        callback.onResult(getStudents(0, 10), 0, 1000)
    }

    /**
     * 加载更多的时候调用
     * @param params LoadRangeParams 配置的加载属性
     * @param callback LoadRangeCallback<StudentBean> 回调
     */
    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<StudentBean>) {
        callback.onResult(getStudents(params.startPosition, params.loadSize))
    }

    /**
     * 模拟获取网络数据
     * @param startPosition Int
     * @param pageSize Int
     * @return List<StudentBean>
     */
    private fun getStudents(startPosition: Int, pageSize: Int): List<StudentBean> {
        Log.i("aaa","获取数据,startPosition:$startPosition,pageSize:$pageSize")
        Thread.sleep(1000)//模拟网络请求时间
        val arrayList = ArrayList<StudentBean>()
        for (i in startPosition until startPosition + pageSize) {
            arrayList.add(StudentBean("id:$i", "名称:$i", "性别:$i"))
        }
        return arrayList
    }

}