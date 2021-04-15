package com.example.demo003_databing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.demo003_jetpack.activity.*
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:
 */
class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1.setOnClickListener(this)
        tv2.setOnClickListener(this)
        tv3.setOnClickListener(this)
        tv4.setOnClickListener(this)
        tv5.setOnClickListener(this)
        tv6.setOnClickListener(this)
        tv7.setOnClickListener(this)
        tv8.setOnClickListener(this)
        tv9.setOnClickListener(this)
        tv10.setOnClickListener(this)
        tv11.setOnClickListener(this)
        tv13.setOnClickListener(this)
        tv14.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv1 -> startActivity(
                Intent(this@MainActivity, Test1Activity::class.java).putExtra("aaa", "好的")
            )
            R.id.tv2 -> startActivity(Intent(this@MainActivity, Test3Activity::class.java))
            R.id.tv3 -> startActivity(Intent(this@MainActivity, Test4Activity::class.java))
            R.id.tv4 -> startActivity(Intent(this@MainActivity, Test5Activity::class.java))
            R.id.tv5 -> startActivity(Intent(this@MainActivity, Test6Activity::class.java))
            R.id.tv6 -> startActivity(Intent(this@MainActivity, Test7Activity::class.java))
            R.id.tv7 -> startActivity(Intent(this@MainActivity, Test8Activity::class.java))
            R.id.tv8 -> startActivity(Intent(this@MainActivity, Test9Activity::class.java))
            R.id.tv9 -> startActivity(Intent(this@MainActivity, Test10Activity::class.java))
            R.id.tv10 -> startActivity(Intent(this@MainActivity, Test11Activity::class.java))
            R.id.tv11 -> startActivity(Intent().apply {
                setClass(
                    this@MainActivity,
                    Test12Activity::class.java
                )
            })
            R.id.tv13 -> startActivity(Intent(this@MainActivity, Test13Activity::class.java))
            R.id.tv14 -> startActivity(Intent(this@MainActivity, Test14Activity::class.java))
        }
    }
}