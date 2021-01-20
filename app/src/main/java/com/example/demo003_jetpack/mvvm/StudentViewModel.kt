package com.example.demo003_jetpack.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.demo003_jetpack.model.StudentBean
import com.example.demo003_jetpack.factory.StudentDataFactory

/**
 *author: Luuuzi
 *Date: 2021-01-20
 *description:
 */
class StudentViewModel : ViewModel() {
    private var listLiveData: LiveData<PagedList<StudentBean>>
    init {
        val factory = StudentDataFactory()
        listLiveData = LivePagedListBuilder<Int, StudentBean>(factory, 10).build()
    }
    fun getStudents():LiveData<PagedList<StudentBean>>{
        return listLiveData
    }
}