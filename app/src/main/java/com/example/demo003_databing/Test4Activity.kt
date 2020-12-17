package com.example.demo003_databing

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.demo003_databing.databinding.ActivityTest4Binding
import com.example.demo003_databing.model.User
import com.example.demo003_databing.util.StringUtils

/**
 * @author: Luuuzi
 * @date: 2020-12-08
 * @description:事件绑定
 */
class Test4Activity : AppCompatActivity() {
    lateinit var user: User
    lateinit var dataBindingUtil: ActivityTest4Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBindingUtil =
            DataBindingUtil.setContentView<ActivityTest4Binding>(this, R.layout.activity_test4)
        user = User("小明", "123456")
        dataBindingUtil.users=user
        dataBindingUtil.userClick=UserPresenterClick()

    }

    //点击事件
    inner class UserPresenterClick {
        fun onUserNameClick(user: User) {
            Toast.makeText(
                this@Test4Activity,
                "name:${user.name},password:${user.password}",
                Toast.LENGTH_SHORT
            ).show()
        }

        fun ofterTextChange(s: Editable) {
            Log.i("aaa","s:$s")
            user.name=s.toString()
            dataBindingUtil.users=user

        }

        fun ofterUserpasswordChange(s: Editable) {
            user.password=s.toString()
            dataBindingUtil.users=user
        }

    }
}