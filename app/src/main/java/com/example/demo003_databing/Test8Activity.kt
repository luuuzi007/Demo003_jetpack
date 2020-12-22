package com.example.demo003_databing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.example.demo003_databing.databinding.ActivityTest8Binding
import kotlin.random.Random

/**
 * @author: Luuuzi
 * @date: 2020-12-22
 * @description:
 */
class Test8Activity : AppCompatActivity() {
    lateinit var mTextViewModel: TextViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mDataBinding =
            DataBindingUtil.setContentView<ActivityTest8Binding>(this, R.layout.activity_test8)
        mDataBinding.click = Test8Click()
        //创建viewmodel

        mTextViewModel =
            ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
                .get(TextViewModel::class.java)
        //添加监听LiveData对象mDataEvent中的数据是否改变
        mTextViewModel.mDataEvent.observe(this,
            Observer<String> { t ->
                //更新ui
                mDataBinding.tvData.text = t
            })

    }

    inner class Test8Click() {
        fun click() {
            object : Thread() {
                //模拟子线程获取数据
                override fun run() {
                    val nextInt = Random.nextInt(100)
//                    mTextViewModel.mDataEvent.value = "内容：$nextInt"//此方法只能在主线程调用(这里调用会报错：Cannot invoke setValue on a background thread）
                    mTextViewModel.mDataEvent.postValue("内容：$nextInt")
                }
            }.start()
        }
    }

    //viewmodel
    class TextViewModel : ViewModel() {
        //创建livedata
        var mDataEvent: MutableLiveData<String> = MutableLiveData()
    }
}