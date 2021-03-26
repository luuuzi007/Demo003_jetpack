package com.example.demo003_jetpack.model

import java.util.*

/**
 *author: Luuuzi
 *Date: 2021-01-20
 *description:
 */
data class StudentBean(var id: String, var name: String, var gender: String) {
    //用于后期比较用到
    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (other == null || other.javaClass != javaClass) {
            return false
        }
        var student: StudentBean = other as StudentBean
        return id == student.id && name == student.name && gender == student.gender
    }
    //用于后期比较用到
    override fun hashCode(): Int {
        return Objects.hash(id, name, gender)
    }
}