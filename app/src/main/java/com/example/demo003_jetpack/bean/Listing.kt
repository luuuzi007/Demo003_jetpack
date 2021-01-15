package com.example.demo003_jetpack.bean

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.example.demo003_jetpack.source.Resource

/**
 *author: Luuuzi
 *Date: 2021-01-15
 *description:UI显示列表和系统其余部分进行交互所必需的数据类
 */
data class Listing<T>(
    val pagedList: LiveData<PagedList<T>>,
    val networkState: LiveData<Resource<String>>,
    val refreshState: LiveData<Resource<String>>,
    val refresh: () -> Unit,
    val retry: () -> Unit
)