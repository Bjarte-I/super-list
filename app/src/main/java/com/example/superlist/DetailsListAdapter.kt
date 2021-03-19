package com.example.superlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_todo_list.view.*
import kotlinx.android.synthetic.main.item_todo_list.view.tv_list_item_title

class DetailsListAdapter(private val todos:MutableList<DetailsTodoItem>) : RecyclerView.Adapter<DetailsListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailsListAdapter.ListViewHolder {
        return DetailsListAdapter.ListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.details_todo_list, parent, false)
        )
    }

    fun addTodo(list: DetailsTodoItem){
        todos.add(list)
        notifyItemInserted(todos.size - 1)
    }

    override fun onBindViewHolder(holder: DetailsListAdapter.ListViewHolder, position: Int) {
        val curList = todos[position]
        holder.itemView.apply {
            tv_list_item_title.text = curList.title
        }

        holder.itemView.button_delete.setOnClickListener() {
            todos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
