package com.example.demo003_databing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest1Binding

import com.example.demo003_databing.model.User

class Test1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val stringExtra = intent.getStringExtra("aaa")
        val string = intent.extras?.getString("aaa")
        Log.i("aaa","stringExtra:$stringExtra")
        Log.i("aaa","String:$string")
        val activityMainBinding = DataBindingUtil.setContentView<ActivityTest1Binding>(this, R.layout.activity_test1)
        val user = User("小红", "123456")
        activityMainBinding.userInfo=user
        activityMainBinding.tvUserName.text="小明"
        activityMainBinding.tvSkip.setOnClickListener {
            startActivity(Intent(this@Test1Activity, Test2Activity::class.java))
        }

    }
}