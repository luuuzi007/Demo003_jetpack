package com.example.demo003_jetpack.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.demo003_databing.R
import com.example.demo003_jetpack.adapter.ArticleAdapter
import com.example.demo003_jetpack.model.DataX
import com.example.demo003_jetpack.mvvm.ArticleViewModel
import kotlinx.android.synthetic.main.activity_test13.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * @author: Luuuzi
 * @Date: 2021-04-01
 * @description:paging2-PageKeyedDataSource
 */
class Test13Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test13)
        Log.i("aaa", "activity13")
        initData()
    }

    private fun initData() {
        rlv_article.layoutManager = LinearLayoutManager(this)
        val articleAdapter = ArticleAdapter()
        rlv_article.adapter = articleAdapter

        val viewModel: ArticleViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(ArticleViewModel::class.java)
//        }
        viewModel.articleList.observe(this,object :Observer<PagedList<DataX>>{
            override fun onChanged(t: PagedList<DataX>?) {
                articleAdapter.submitList(t)
                Log.i("aaa","t.size:${t?.size}")
            }
        })
    }
}