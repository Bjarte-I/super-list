package com.example.superlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo_list.view.*

class TodoListAdapter(private val lists:MutableList<ListItem>) : RecyclerView.Adapter<TodoListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo_list, parent, false)
        )
    }

    fun addList(list: ListItem) {
        lists.add(list)
        notifyItemInserted(lists.size - 1)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val curList = lists[position]
        holder.itemView.apply {
            tv_list_item_title.text = curList.title
        }

        holder.itemView.button_delete.setOnClickListener() {
            lists.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, DetailsListActivity::class.java).also {
                startActivity(holder.itemView.context, it, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return lists.size
    }


}