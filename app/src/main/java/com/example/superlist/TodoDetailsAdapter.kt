package com.example.superlist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.DetailsTodoListBinding
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList

class TodoDetailsAdapter(private var todos:List<Todo>, private val onTodoClicked:(Todo) -> Unit) : RecyclerView.Adapter<TodoDetailsAdapter.ViewHolder>() {



    class ViewHolder(val binding:DetailsTodoListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo, onTodoClicked: (Todo) -> Unit){
            binding.tvTodoTitle.text = todo.title
            binding.cbTodo.isChecked = todo.isChecked
            toggleStrikeThrough(binding.tvTodoTitle, todo.isChecked)
            binding.cbTodo.setOnCheckedChangeListener { _, isChecked -> //Execute toggleStrikeThrough when the _todo checkbox changes.
                toggleStrikeThrough(binding.tvTodoTitle, isChecked)
                onTodoClicked(todo)
            }
            binding.buttonDetailsDelete.setOnClickListener {
                TodoListManager.instance.removeTodo(todo, binding.root.context)
            }
        }

        private fun toggleStrikeThrough(tv_list_item_title: TextView, isChecked: Boolean){
            if(isChecked){
                tv_list_item_title.paintFlags = tv_list_item_title.paintFlags or STRIKE_THRU_TEXT_FLAG
            } else {
                tv_list_item_title.paintFlags = tv_list_item_title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DetailsTodoListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    fun updateCollection(newTodos:List<Todo>){
        todos = newTodos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo, onTodoClicked)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
