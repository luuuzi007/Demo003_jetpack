package com.example.demo003_jetpack.repository

import com.example.demo003_jetpack.bean.Listing
import com.example.demo003_jetpack.bean.StudentBean

/**
 *author: Luuuzi
 *Date: 2021-01-15
 *description:给不同的Repository实现共享的通用接口
 */
interface StudentRepository {
    fun getStudentList(pageSize:Int):Listing<StudentBean?>
}