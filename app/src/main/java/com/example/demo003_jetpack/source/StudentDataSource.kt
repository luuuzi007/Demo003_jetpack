package com.example.demo003_jetpack.source


import android.util.Log
import androidx.paging.PositionalDataSource
import com.example.demo003_jetpack.model.StudentBean

/**
 *author: Luuuzi
 *Date: 2021-01-15
 *description: DataSource负责加载第一页以及后面每一页数据
 */
open class StudentDataSource : PositionalDataSource<StudentBean>() {
    override fun loadInitial(
        params: LoadInitialParams,
        callback: LoadInitialCallback<StudentBean>
    ) {
        callback.onResult(getStudents(0, 10), 0, 1000)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<StudentBean>) {
        callback.onResult(getStudents(params.startPosition, params.loadSize))
    }

    private fun getStudents(startPosition: Int, pageSize: Int): List<StudentBean> {
        Thread.sleep(3000)//模拟网络请求时间
        Log.i("aaa","获取数据,startPosition:$startPosition,pageSize:$pageSize")
        val arrayList = ArrayList<StudentBean>()
        for (i in startPosition until startPosition + pageSize) {
            arrayList.add(StudentBean("id:$i", "名称:$i", "性别:$i"))
        }
        return arrayList
    }

}