package com.example.demo003_jetpack.mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.demo003_jetpack.factory.ArticleDataFactory
import com.example.demo003_jetpack.model.DataX

/**
 * @author: Luuuzi
 * @Date: 2021-04-08
 * @description:
 */
class ArticleViewModel : ViewModel() {
    var articleList: LiveData<PagedList<DataX>>

    init {
        val build = PagedList.Config.Builder()
            .setPageSize(11)
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(30)
            .setPrefetchDistance(5)
            .build()
        val articleDataFactory = ArticleDataFactory()
        articleList = LivePagedListBuilder<Int, DataX>(articleDataFactory, build).build()
    }
}