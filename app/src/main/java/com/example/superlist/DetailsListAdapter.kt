package com.example.superlist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.details_todo_list.view.*
import kotlinx.android.synthetic.main.item_todo_list.view.*
import kotlinx.android.synthetic.main.item_todo_list.view.tv_list_item_title

class DetailsListAdapter(private val todos:MutableList<DetailsTodoItem>) : RecyclerView.Adapter<DetailsListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.details_todo_list, parent, false)
        )
    }

    fun addTodo(todo: DetailsTodoItem){
        todos.add(todo)
        notifyItemInserted(todos.size - 1)
    }

    private fun toggleStrikeThrough(tv_list_item_title: TextView, isChecked: Boolean){
        if(isChecked){
            tv_list_item_title.paintFlags = tv_list_item_title.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tv_list_item_title.paintFlags = tv_list_item_title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val curTodo = todos[position]
        holder.itemView.apply {
            tv_list_item_title.text = curTodo.title
            cb_todo.isChecked = curTodo.isChecked
            toggleStrikeThrough(tv_list_item_title, curTodo.isChecked)
            cb_todo.setOnCheckedChangeListener { _, isChecked ->
                toggleStrikeThrough(tv_list_item_title, isChecked)
                curTodo.isChecked = !curTodo.isChecked
            }
        }

        holder.itemView.button_details_delete.setOnClickListener {
            todos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
