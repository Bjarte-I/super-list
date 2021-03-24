package com.example.superlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.models.TodoList
import kotlinx.android.synthetic.main.item_todo_list.view.*
import java.lang.Math.floor

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val todoLists = TodoListsSingleton.SINGLETON_TODO_LISTS.todoLists

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo_list, parent, false))
    }

    fun addList(todoList: TodoList) {
        todoLists.add(todoList)
        notifyItemInserted(todoLists.size - 1)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.apply {
            tv_todo_title.text = todoLists[position].title

            button_delete.setOnClickListener {
                todoLists.removeAt(position)
                notifyDataSetChanged()
            }

            setOnClickListener {
                Intent(holder.itemView.context, TodoDetailsActivity::class.java).also {
                    it.putExtra("EXTRA_LIST_POSITION", position)
                    startActivity(holder.itemView.context, it, null)
                }
            }

            var checkedTodosCount = 0 //todo fix bug with checkboxes
            for(todo in todoLists[position].listOfTodos){
                if(todo.isChecked){
                    checkedTodosCount++
                }
            }
            pb_list_progress.progress = (checkedTodosCount * 100.0 / todoLists[position].listOfTodos.size).toInt()
        }
    }

    override fun getItemCount(): Int {
        return todoLists.size
    }


}