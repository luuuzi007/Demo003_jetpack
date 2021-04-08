package com.example.demo003_jetpack.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo003_databing.R
import com.example.demo003_databing.databinding.ActivityTest12Binding
import com.example.demo003_jetpack.adapter.StudentAdapter
import com.example.demo003_jetpack.model.StudentBean
import com.example.demo003_jetpack.mvvm.StudentViewModel


/**
 * @author: Luuuzi
 * @date: 2020-12-31
 * @description:paging使用
 */
class Test12Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest12Binding =
            DataBindingUtil.setContentView<ActivityTest12Binding>(this, R.layout.activity_test12)
        activityTest12Binding.rlvPaging.layoutManager = LinearLayoutManager(this)
        val adapter = StudentAdapter()
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(StudentViewModel::class.java)

        //4、数据改变后更新界面
        viewModel.getStudents().observe(this, object : Observer<PagedList<StudentBean>> {
            override fun onChanged(t: PagedList<StudentBean>?) {
                adapter.submitList(t)
            }
        })
        activityTest12Binding.rlvPaging.adapter = adapter
    }
}