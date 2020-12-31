package com.example.demo003_databing.mvvm


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @author: Luuuzi
 * @date: 2020-12-21
 * @description:viewmodel层
 */
class ImageViewModel : ViewModel() {
    var mImage: MutableLiveData<Data<Image>> = MutableLiveData()//livedata绑定的数据源
    private var imageRepertory: ImageRepertory = ImageRepertory()
    private var idx: Int = 0

    //第一次获取图片
    fun loadImag() {
        idx=0
        imageRepertory.getApi().getImage("js", idx, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ImageBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ImageBean) {
                    Log.i("aaa", "url:${t.images[0].url}")
                    mImage.value = Data(t.images[0], null)
                }

                override fun onError(e: Throwable) {
                    Log.i("aaa", "error:${e.message}")
                    mImage.value = Data(null, e.message)
                }

                override fun onComplete() {
                }
            })
    }

    //下一张
    fun nextImage() {
        imageRepertory.getApi().getImage("js", ++idx, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ImageBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ImageBean) {
                    mImage.value = Data(t.images[0], null)
                }

                override fun onError(e: Throwable) {
                    mImage.value = Data(null, e.message)
                }

                override fun onComplete() {
                }
            })
    }

    //上一张
    fun previousImages() {
        if (idx == 0) {
            mImage.value = Data(null, "已经是第一张了")
            return
        }
        imageRepertory.getApi().getImage("js", --idx, 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<ImageBean> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: ImageBean) {
                    mImage.value = Data(t.images[0], null)
                }

                override fun onError(e: Throwable) {
                    mImage.value = Data(null, e.message)
                }

                override fun onComplete() {
                }
            })
    }
}