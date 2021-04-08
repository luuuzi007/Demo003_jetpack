package com.example.demo003_jetpack.net

import com.example.demo003_jetpack.model.ArticleBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:
 */
interface Api {
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Call<ArticleBean>
}