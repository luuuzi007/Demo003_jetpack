package com.example.demo003_databing.model

import androidx.databinding.ObservableField
import androidx.databinding.ObservableFloat

/**
 * @author: Luuuzi
 * @date: 2020-12-02
 * @description:
 */
class ObservableGoods(name: String, details: String, price: Float) {
    var name2:ObservableField<String>
    var details2:ObservableField<String>
    var price2:ObservableFloat
    init {
        this.name2= ObservableField(name)
        this.details2=ObservableField(details)
        this.price2=ObservableFloat(price)
    }

}