package com.example.demo003_jetpack.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.demo003_databing.R
import com.example.demo003_jetpack.model.StudentBean

/**
 *author: Luuuzi
 *Date: 2021-01-20
 *description:
 */
class StudentAdapter : PagedListAdapter<StudentBean, StudentAdapter.ViewHolder> {
    companion object {
        //1、定义比较逻辑
        private val dief_student: DiffUtil.ItemCallback<StudentBean> =
            object : DiffUtil.ItemCallback<StudentBean>() {
                override fun areItemsTheSame(oldItem: StudentBean, newItem: StudentBean): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: StudentBean,
                    newItem: StudentBean
                ): Boolean {
                    return oldItem == newItem
                }
            }


    }
    //2、构建方法设置比较属性
    constructor() : super(dief_student)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_studen, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = getItem(position)
        if (student == null) {
            holder.tvId.text = "加载中..."
            holder.tvName.text = "加载中..."
            holder.tvGender.text = "加载中..."
        } else {
            holder.tvId.text = student.id
            holder.tvName.text = student.name
            holder.tvGender.text = student.gender
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvId: TextView
        var tvName: TextView
        var tvGender: TextView

        init {
            tvId = itemView.findViewById(R.id.tv_id)
            tvName = itemView.findViewById(R.id.tv_name)
            tvGender = itemView.findViewById(R.id.tv_gender)
        }

    }
}