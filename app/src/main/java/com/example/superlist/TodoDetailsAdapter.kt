package com.example.superlist

import android.graphics.Paint.STRIKE_THRU_TEXT_FLAG
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.models.Todo
import kotlinx.android.synthetic.main.details_todo_list.view.*
import kotlinx.android.synthetic.main.item_todo_list.view.tv_todo_title

class TodoDetailsAdapter(listItemPosition:Int) : RecyclerView.Adapter<TodoDetailsAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val listItems = TodoListsSingleton.SINGLETON_TODO_LISTS.todoLists
    private val currentTodoList = listItems[listItemPosition]

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.details_todo_list, parent, false)
        )
    }

    fun addTodo(todo: Todo){
        currentTodoList.listOfTodos.add(todo) //Add the _todo to the singleton
        notifyItemInserted(currentTodoList.listOfTodos.size - 1)
    }

    private fun updateProgress(){
        var checkedTodosCount = 0
        for(todo in currentTodoList.listOfTodos){
            if(todo.isChecked){
                checkedTodosCount++
            }
        }
        currentTodoList.progress = (checkedTodosCount * 100.0 / currentTodoList.listOfTodos.size).toInt()
    }

    private fun toggleStrikeThrough(tv_list_item_title: TextView, isChecked: Boolean){
        if(isChecked){
            tv_list_item_title.paintFlags = tv_list_item_title.paintFlags or STRIKE_THRU_TEXT_FLAG
        } else {
            tv_list_item_title.paintFlags = tv_list_item_title.paintFlags and STRIKE_THRU_TEXT_FLAG.inv()
        }
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val currentlyClickedTodo = currentTodoList.listOfTodos[position]
        holder.itemView.apply {
            tv_todo_title.text = currentlyClickedTodo.title
            cb_todo.isChecked = currentlyClickedTodo.isChecked
            toggleStrikeThrough(tv_todo_title, currentlyClickedTodo.isChecked)
            cb_todo.setOnCheckedChangeListener { _, isChecked -> //Execute when the _todo checkbox changes.
                toggleStrikeThrough(tv_todo_title, isChecked)
                currentlyClickedTodo.isChecked = !currentlyClickedTodo.isChecked
                updateProgress()
                // Todo update the pb_details_progress somehow.
            }

            button_details_delete.setOnClickListener {
                currentTodoList.listOfTodos.removeAt(position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return currentTodoList.listOfTodos.size
    }

}
