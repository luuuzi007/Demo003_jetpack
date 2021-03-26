package com.example.demo003_jetpack.factory


import androidx.paging.DataSource
import com.example.demo003_jetpack.model.StudentBean
import com.example.demo003_jetpack.source.StudentDataSource

/**
 * author: Luuuzi
 * Date: 2021-01-15
 * description:2、工厂类，负责创建DataSource
 */
class StudentDataFactory : DataSource.Factory<Int, StudentBean>() {
    override fun create(): DataSource<Int, StudentBean> {
        return StudentDataSource()
    }
}