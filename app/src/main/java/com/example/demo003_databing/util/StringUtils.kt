package com.example.demo003_databing.util

/**
 * @author: Luuuzi
 * @date: 2020-12-09
 * @description:databing调用静态方法
 */
class StringUtils {
    companion object {
        @JvmStatic
        fun UpperCase(str: String): String {
            return str.toUpperCase()
        }
    }
}