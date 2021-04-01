package com.example.demo003_databing

import android.os.Bundle
import android.view.View
import android.view.ViewStub
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest5Binding
import com.example.demo003_databing.databinding.LayoutViewBinding
import com.example.demo003_databing.model.Goods
import com.example.demo003_databing.model.User

/**
 * @author: Luuuzi
 * @date: 2020-12-09
 * @description:
 */
class Test5Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //只关联了activity_test5.xml没关联layout_view.xml
        val dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest5Binding>(this, R.layout.activity_test5)
        dataBindingUtil.userInfo = User("好的", "123456")
        val goods = Goods()
        goods.details = "帅气"
        dataBindingUtil.good = goods

        //获取到viewstub(只有调用了inflateviewstub才会显示
        val inflate = dataBindingUtil.viewStub.viewStub?.inflate()
        //设置可见性
        dataBindingUtil.tv1.setOnClickListener {
            if (inflate?.visibility == View.VISIBLE) {
                inflate.visibility = View.GONE
            } else {
                inflate?.visibility = View.VISIBLE
            }
        }

        dataBindingUtil.viewStub.setOnInflateListener(object : ViewStub.OnInflateListener {
            override fun onInflate(stub: ViewStub?, inflated: View?) {
                //如果在 xml 中没有使用 bind:userInfo="@{userInf}" 对 viewStub 进行数据绑定
                //那么可以在此处进行手动绑定
                    val bind = DataBindingUtil.bind<LayoutViewBinding>(inflated!!)
                    bind?.userInfo = User("小米", "123456")
            }
        })
    }
}