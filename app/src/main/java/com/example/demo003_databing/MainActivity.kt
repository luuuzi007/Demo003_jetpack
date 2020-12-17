package com.example.demo003_databing

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:
 */
class MainActivity:AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv1.setOnClickListener(this)
        tv2.setOnClickListener(this)
        tv3.setOnClickListener(this)
        tv4.setOnClickListener(this)
        tv5.setOnClickListener(this)
        tv6.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv1->startActivity(
                Intent(this@MainActivity,Test1Activity::class.java).let{
                    it.putExtra("aaa","好的")
                })
            R.id.tv2->startActivity(Intent(this@MainActivity,Test3Activity::class.java))
            R.id.tv3->startActivity(Intent(this@MainActivity,Test4Activity::class.java))
            R.id.tv4->startActivity(Intent(this@MainActivity,Test5Activity::class.java))
            R.id.tv5->startActivity(Intent(this@MainActivity,Test6Activity::class.java))
            R.id.tv6->startActivity(Intent(this@MainActivity,Test7Activity::class.java))
        }
    }
}