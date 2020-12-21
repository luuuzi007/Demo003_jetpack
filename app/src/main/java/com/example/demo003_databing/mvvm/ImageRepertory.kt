package com.example.demo003_databing.mvvm

import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * @author: Luuuzi
 * @date: 2020-12-21
 * @description:网络请求封装
 */
class ImageRepertory {
    var mRetrofit: Retrofit

    init {
        mRetrofit = Retrofit.Builder()
            .baseUrl("https://cn.bing.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
    fun getApi(): Api {
        return mRetrofit.create(Api::class.java)
    }
    interface Api {
        @GET("HPImageArchive.aspx")
        fun getImage(
            @Query("format") format: String,
            @Query("idx") idx: Int,
            @Query("n") n: Int
        ): Observable<ImageBean>
    }
}