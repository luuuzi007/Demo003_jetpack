package com.example.demo003_databing.mvvm

/**
 * @author: Luuuzi
 * @date: 2020-12-21
 * @description:包装类来维护数据的状态和错误信息
 */
data class Data<T>(var mData: T?, var mErrorMsg: String?)