package com.example.superlist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.DetailsTodoListBinding
import com.example.superlist.models.Todo

class TodoDetailsAdapter(private var todos: List<Todo>, private val onCheckboxChanged: (Todo) -> Unit, private val onDeleteClicked: (Todo) -> Unit) : RecyclerView.Adapter<TodoDetailsAdapter.ViewHolder>() {



    class ViewHolder(val binding:DetailsTodoListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(todo: Todo, onCheckboxChanged: (Todo) -> Unit, onDeleteClicked: (Todo) -> Unit){
            binding.tvTodoTitle.text = todo.title
            binding.cbTodo.isChecked = todo.isChecked
            toggleStrikeThrough(binding.tvTodoTitle, todo.isChecked)
            binding.cbTodo.setOnClickListener {  //setOnCheckedChangeListener would not only trigger on the checkbox changing, but also when I removed a _todo after changing a checkbox above the one I was deleting for some reason.
                onCheckboxChanged(todo)
                toggleStrikeThrough(binding.tvTodoTitle, todo.isChecked)
            }
            binding.buttonDetailsDelete.setOnClickListener {
                onDeleteClicked(todo)
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
        holder.bind(todo, onCheckboxChanged, onDeleteClicked)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

}
