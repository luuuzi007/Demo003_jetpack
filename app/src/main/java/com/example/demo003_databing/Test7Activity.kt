package com.example.demo003_databing

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.BindingAdapter
import androidx.databinding.BindingConversion
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest7Binding
import com.example.demo003_databing.util.ImageUtils
import kotlin.random.Random

/**
 * @author: Luuuzi
 * @date: 2020-12-15
 * @description:BindingAdapter和BindingConversion使用
 */
class Test7Activity : AppCompatActivity() {
    lateinit var dataBindingUtil: ActivityTest7Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest7Binding>(this, R.layout.activity_test7)
        dataBindingUtil.image = ImageUtils("测试")
        dataBindingUtil.hd = HandlerOnclick()
    }

    //    放在此处则报错
//    @BindingAdapter("aaa")
//    fun loadImage(imageView: ImageView, cc: String) {
//        Log.i("aaa", "rul:$cc")
//    }

    inner class HandlerOnclick {
        fun onClick(imageUtils: ImageUtils): Boolean {
            imageUtils.url.set("xxxxx${Random.nextInt(1000)}")
            //修改原生属性(这个改变
            dataBindingUtil.button.text = "修改内容${Random.nextInt(1000)}"
            return true
        }
    }
}

/**
 *通过BindingAdapter绑定url属性，当url发生变化时，调用此方法(可以设置加载图片)
 * BindingAdapter与对应的方法参数个数和类型有关，与方法名和参数名无关(view类型以及bind参数类型)
 */
@BindingAdapter("aaa")
fun loadImage(imageView: ImageView, cc: String) {
    //当aaa属性的值变化时，此方法会执行
    Log.i("aaa", "rul:$cc")
}

/**
 * 覆盖原生属性,但是对java代码设置无效
 */
@BindingAdapter("android:text")
fun setText(view: AppCompatButton, text: String) {
    Log.i("aaa", "原生属性：$text")
    view.text = "$text-Button"
}

/**
 * 数据转换
 */
@BindingConversion
fun conversionString(text: String):String{
    return "$text --conversation"
}

/**
 * 类型转换 将string转换成drawable
 */
@BindingConversion
fun conversionType(text: String): Drawable {
    if (text == "红色") {
        return ColorDrawable(Color.RED)
    } else if (text == "蓝色") {
        return ColorDrawable(Color.BLUE)
    } else {
        return ColorDrawable(Color.TRANSPARENT)
    }
}

/**
 * 类型转换2 将string转换成int
 */
@BindingConversion
fun conversionType2(text: String): Int {
    if (text == "红色") {
        return Color.RED
    } else if (text == "蓝色") {
        return Color.BLUE
    } else {
        return Color.TRANSPARENT
    }
}

