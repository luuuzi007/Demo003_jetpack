package com.example.demo003_databing

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.demo003_databing.databinding.ActivityTest9Binding
import com.example.demo003_databing.mvvm.Data
import com.example.demo003_databing.mvvm.Image
import com.example.demo003_databing.mvvm.ImageViewModel


/**
 * @author: Luuuzi
 * @date: 2020-12-21
 * @description:mvvm实现列表 view层
 */
class Test9Activity : AppCompatActivity() {
    lateinit var dataBindingUtil: ActivityTest9Binding
    lateinit var imageViewModel: ImageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //1、初始化databinding：实现数据和view的绑定
        dataBindingUtil = DataBindingUtil.setContentView(this, R.layout.activity_test9)
        dataBindingUtil.click = Test9Click()
        //2、初始化viewmodel:获取数据，实现与数据层的解耦
        imageViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(ImageViewModel::class.java)
        //viewmodel获取到数据监听
        imageViewModel.mImage.observe(this, object : Observer<Data<Image>> {
            override fun onChanged(t: Data<Image>?) {
                if (t?.mErrorMsg != null) {//加载错误提示
                    Toast.makeText(this@Test9Activity, t.mErrorMsg, Toast.LENGTH_SHORT).show()
                    return
                }
                //设置最新数据
                dataBindingUtil.imageBean = t?.mData
            }
        })
    }

    inner class Test9Click {
        fun loadImag() {
            imageViewModel.loadImag()
        }

        fun nextImage() {
            imageViewModel.nextImage()
        }

        fun previousImages() {
            imageViewModel.previousImages()
        }
    }
}

/**
 * 自定义属性
 */
@BindingAdapter("url")
fun loadImage(iv: AppCompatImageView, url: String) {
    Glide.with(iv.context)
        .load(url)
        .into(iv)
}