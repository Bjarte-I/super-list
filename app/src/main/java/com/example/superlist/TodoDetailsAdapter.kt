package com.example.superlist

import android.content.Intent
import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.DetailsTodoListBinding
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import kotlinx.android.synthetic.main.details_todo_list.view.*
import kotlinx.android.synthetic.main.item_todo_list.view.tv_list_title

class TodoDetailsAdapter(private var todos:List<Todo>) : RecyclerView.Adapter<TodoDetailsAdapter.ViewHolder>() {



    class ViewHolder(val binding:DetailsTodoListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo){
            binding.tvTodoTitle.text = todo.title
            binding.cbTodo.isChecked = todo.isChecked
            toggleStrikeThrough(binding.tvTodoTitle, todo.isChecked)
            binding.cbTodo.setOnCheckedChangeListener { _, isChecked -> //Execute toggleStrikeThrough when the _todo checkbox changes.
                toggleStrikeThrough(binding.tvTodoTitle, isChecked)
                todo.isChecked = !todo.isChecked

                //todo update progress bar somehow
            }
            binding.buttonDetailsDelete.setOnClickListener {
                val receivedTodoList = TodoListHolder.PickedTodoList
                if(receivedTodoList != null){
                    TodoListManager.instance.removeTodo(todo, receivedTodoList)
                }
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

    public fun updateCollection(newTodos:List<Todo>){
        todos = newTodos
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = todos[position]
        holder.bind(todo)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
