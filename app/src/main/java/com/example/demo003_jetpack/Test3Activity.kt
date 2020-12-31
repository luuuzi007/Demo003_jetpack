package com.example.demo003_databing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest3Binding
import com.example.demo003_databing.model.ObservableGoods

/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:双向数据绑定
 */
class Test3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest3Binding>(this, R.layout.activity_test3)
            dataBindingUtil.goods= ObservableGoods("小红","漂亮",18f)

    }
}