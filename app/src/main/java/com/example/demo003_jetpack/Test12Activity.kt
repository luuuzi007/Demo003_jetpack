package com.example.demo003_jetpack

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.R
import com.example.demo003_databing.databinding.ActivityTest12Binding
import kotlinx.coroutines.*
import java.lang.System.*

/**
 * @author: Luuuzi
 * @date: 2020-12-31
 * @description:paging
 */
class Test12Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityTest12Binding =
            DataBindingUtil.setContentView<ActivityTest12Binding>(this, R.layout.activity_test12)


    }

}