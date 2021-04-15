package com.example.demo003_jetpack.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.demo003_databing.R
import com.example.demo003_jetpack.lifecycle.CallBack
import com.example.demo003_jetpack.lifecycle.MyLifecycleObserver
import kotlinx.android.synthetic.main.activity_14.*

/**
 * @author: Luuuzi
 * @Date: 2021-04-12
 * @description:Lifecycles基本使用
 */
//, LifecycleOwner
class Test14Activity : AppCompatActivity() {
    //创建注册对象
//    var lifecycleRegister: LifecycleRegistry = LifecycleRegistry(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_14)
        tv.setOnClickListener { v -> startActivity(Intent(this, Test13Activity::class.java)) }
        //3、创建观察者对象
        val myObserver = MyLifecycleObserver(lifecycle, object : CallBack {
            override fun update(str: String) {
                Toast.makeText(this@Test14Activity, str, Toast.LENGTH_SHORT).show()
            }
        })
        //4、将观察者和被观察者绑定
        lifecycle.addObserver(myObserver)
//        lifecycleRegister.currentState = Lifecycle.State.CREATED
    }


//    override fun getLifecycle(): Lifecycle {
//        return lifecycleRegister
//    }

    //注意：因为AppCompatActivity的父类(ComponentActivity)本身实现了LifecycleOwner接口，所有就不用去设置状态
//    override fun onResume() {
//        super.onResume()
//        lifecycleRegister.markState(Lifecycle.State.RESUMED)
//    }

//    override fun onDestroy() {
//        super.onDestroy()
//        lifecycleRegister.markState(Lifecycle.State.DESTROYED)
//    }
}