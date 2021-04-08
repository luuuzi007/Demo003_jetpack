package com.example.demo003_jetpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo003_databing.R
import com.example.demo003_jetpack.model.DataX

/**
 * @author: Luuuzi
 * @Date: 2021-04-07
 * @description:
 */
class ArticleAdapter : PagedListAdapter<DataX, ArticleAdapter.Holder> {
    companion object {
        val dief: DiffUtil.ItemCallback<DataX> = object : DiffUtil.ItemCallback<DataX>() {
            override fun areItemsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem.courseId == newItem.courseId
            }

            override fun areContentsTheSame(oldItem: DataX, newItem: DataX): Boolean {
                return oldItem == newItem
            }
        }
    }

    constructor() : super(dief)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        holder.tv_article.text=item?.chapterName
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tv_article: AppCompatTextView

        init {
            tv_article = itemView.findViewById(R.id.tv_article)
        }
    }

}
