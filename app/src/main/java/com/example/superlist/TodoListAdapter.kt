package com.example.superlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.ItemTodoListBinding
import com.example.superlist.models.TodoList

class TodoListAdapter(private var todoLists:List<TodoList>, private val onTodoListClicked:(TodoList) -> Unit) : RecyclerView.Adapter<TodoListAdapter.ViewHolder>() {
    class ViewHolder(val binding:ItemTodoListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(todoList: TodoList, onTodoListClicked: (TodoList) -> Unit) {
            binding.tvListTitle.text = todoList.title
            val progress:Int = TodoListManager.instance.calculateListProgress(todoList)
            binding.pbListProgress.progress = progress
            binding.buttonDelete.setOnClickListener {
                TodoListManager.instance.removeTodoList(todoList)
            }
            binding.clList.setOnClickListener {
                onTodoListClicked(todoList)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateCollection(newTodoLists:List<TodoList>) {
        todoLists = newTodoLists
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = todoLists.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoList = todoLists[position]
        holder.bind(todoList, onTodoListClicked)
    }
}
