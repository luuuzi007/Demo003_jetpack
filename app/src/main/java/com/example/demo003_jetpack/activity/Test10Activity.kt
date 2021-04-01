package com.example.demo003_jetpack.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.arraySetOf

class Test10Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val linkedHashSet = LinkedHashSet<Int>()
        linkedHashSet.add(1)
        linkedHashSet.add(2)
        linkedHashSet.add(3)
        linkedHashSet.add(4)
        val linkedHashSet1 = LinkedHashSet<Int>()
        linkedHashSet1.add(3)
        linkedHashSet1.add(4)
        linkedHashSet1.add(5)
        linkedHashSet1.add(7)
        var linkedHashSet2 = LinkedHashSet<Int>()
        linkedHashSet2.addAll(linkedHashSet)
        linkedHashSet2.addAll(linkedHashSet1)
        linkedHashSet2 =
            (arraySetOf<Int>(1, 2, 3) + arraySetOf<Int>(3, 4, 5, 6, 7)) as LinkedHashSet<Int>
    }
}