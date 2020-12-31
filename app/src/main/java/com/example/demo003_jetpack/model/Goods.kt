package com.example.demo003_databing.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.example.demo003_databing.BR

/**
 * @author: Luuuzi
 * @date: 2020-11-27
 * @description:
 */
class Goods : BaseObservable() {
    //开启:使用java8高级api(不受版本限制)
//        coreLibraryDesugaringEnabled true
    //Bindable注解只能修饰public的属性
    @Bindable
    public var name: String = ""
        set(value) {
            field = value
            //只更新指定字段
            notifyPropertyChanged(BR.name)
        }

    @set:Bindable
    var details: String = ""
        set(value) {
            field = value
            //更新所有字段
            notifyChange()
        }

    var price: Float = 0f
        set(value) {
            field = value
        }
}