package com.example.demo003_databing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableArrayMap
import com.example.demo003_databing.databinding.ActivityTest2Binding
import com.example.demo003_databing.model.Goods
import com.example.demo003_databing.model.ObservableGoods
import java.util.*


/**
 * @author: Luuuzi
 * @date: 2020-12-02
 * @description:
 */
class Test2Activity : AppCompatActivity() {
    lateinit var goods: Goods
    lateinit var observableGoods: ObservableGoods
    private lateinit var dataBindingUtil: ActivityTest2Binding
//    lateinit var list: ObservableArrayList<String>
//    lateinit var map: ObservableArrayMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest2Binding>(this, R.layout.activity_test2)
        goods = Goods()
        goods.name = "小红"
        goods.details = "漂亮"
        goods.price = 2000f
        dataBindingUtil.goods = goods
        dataBindingUtil.click = ClickHandler()
        //监听可观察对象的属性改变
        goods.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                when (propertyId) {
                    com.example.demo003_databing.BR.name ->
                        Log.i("aaa", "name")
                    com.example.demo003_databing.BR.details ->
                        Log.i("aaa", "details")
                    com.example.demo003_databing.BR._all ->
                        Log.i("aaa", "all")
                    else ->
                        Log.i("aaa", "未知字段")
                }
            }
        })
        observableGoods = ObservableGoods("张三", "英俊", 32f)
        dataBindingUtil.observableGoods = observableGoods
        dataBindingUtil.click2 = ClickHandler2()

//ObservableCollection使用
        val list = ObservableArrayList<String>()
        list.add("测试数据")
        dataBindingUtil.list = list

        val map = ObservableArrayMap<String,String>()
        map["name"] = "默认值"
        dataBindingUtil.map = map
        //设置对应的下标和key
        dataBindingUtil.index = 0
        dataBindingUtil.key = "name"

        dataBindingUtil.btnModify3.setOnClickListener {
            val toFloat = Random().nextInt(100).toFloat()
            list[0] = "小张的年龄$toFloat 岁"
            map["name"] = "小宇：$toFloat"
        }
    }

    //点击事件class
    inner class ClickHandler {
        //只更新name属性
        fun clickName() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.name = "小明:$toFloat"
            goods.price = toFloat

        }

        //会更新所有属性
        fun clickDetails() {
            val toFloat = Random().nextInt(100).toFloat()
            goods.details = "帅气:$toFloat"
            goods.price = toFloat
        }
    }

    //点击事件class2(ObservableField)
    inner class ClickHandler2 {
        //只更新name属性
        fun clickName() {
            Log.i("aaa", "clickName2")
            val toFloat = Random().nextInt(100).toFloat()
            observableGoods.name2.set("李四:$toFloat")
        }

        //会更新所有属性
        fun clickDetails() {
            Log.i("aaa", "clickDetails2")
            val toFloat = Random().nextInt(100).toFloat()
            observableGoods.details2.set("高大：$toFloat")
            observableGoods.price2.set(toFloat)
        }
    }
}