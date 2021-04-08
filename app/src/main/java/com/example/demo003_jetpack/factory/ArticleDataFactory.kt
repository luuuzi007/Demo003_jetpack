package com.example.demo003_jetpack.factory

import androidx.paging.DataSource
import com.example.demo003_jetpack.model.DataX
import com.example.demo003_jetpack.source.ArticleDataSource

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:
 */
class ArticleDataFactory:DataSource.Factory<Int,DataX>() {
    override fun create(): DataSource<Int, DataX> {
        return ArticleDataSource()
    }
}