package com.example.demo003_databing

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest3Binding
import com.example.demo003_jetpack.model.User2

/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:双向数据绑定
 */
class Test3Activity : AppCompatActivity() {
    lateinit var binding: ActivityTest3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_test3)
        val user = User2("小蓝")
        binding.user2 = user
        binding.persenter = UserPresenter(user, binding)
    }

    class UserPresenter(
        private val user: User2,
        private val binding: ActivityTest3Binding
    ) {
        //当内容改变后才将数据设置给databinding
        fun afterTextChanged(s: Editable) {
            user.name2 = s.toString()
            //设置数据给databinding
            binding.user2 = user
            Log.i("aaa","内容：${binding.tvName2.text}")
        }
    }
}