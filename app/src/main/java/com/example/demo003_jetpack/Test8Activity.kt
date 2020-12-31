package com.example.demo003_databing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.demo003_databing.databinding.ActivityTest8Binding
import com.example.demo003_databing.util.NetWorkLiveData
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
//        mTextViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory(application))
//            .get(TextViewModel::class.java)
        //自定义factory
        mTextViewModel =
            ViewModelProvider(this, KeyFactory("application"))
                .get(TextViewModel::class.java)
        Log.i("aaa", "key:${mTextViewModel.mKey}")
        //添加监听LiveData对象mDataEvent中的数据是否改变
        mTextViewModel.mDataEvent.observe(this,
            Observer<String> { t ->
                //更新ui
                mDataBinding.tvData.text = t
            })

    }

    inner class Test8Click {
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

        fun click2() {
            //监听网络变化
            NetWorkLiveData.getInstance(this@Test8Activity)?.observe(this@Test8Activity, Observer {
                Log.i("aaa", "netWorkInfo:$it")
            })
        }
    }

    //viewmodel
    class TextViewModel(var mKey: String) : ViewModel() {

        //创建livedata
        var mDataEvent: MutableLiveData<String> = MutableLiveData()
    }

    //自定义factory
    class KeyFactory(var mKey: String) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return TextViewModel(mKey) as T
        }
    }
}