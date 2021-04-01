package com.example.demo003_databing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest6Binding
import com.example.demo003_databing.util.ImageUtils
import kotlin.random.Random

/**
 * @author: Luuuzi
 * @date: 2020-12-10
 * @description: databinding常用注解
 */
class Test6Activity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest6Binding>(this, R.layout.activity_test6)
            dataBindingUtil.handler=Handler()
    }
    class Handler{
        fun onClick(imageUtils: ImageUtils):Boolean{
            imageUtils.url.set("xxxxx ${Random.nextInt(1000)}")
            return true
        }
    }
}