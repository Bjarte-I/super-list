package com.example.superlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.models.TodoList
import kotlinx.android.synthetic.main.activity_todo_lists.*
import kotlinx.android.synthetic.main.item_todo_list.*

class TodoListsActivity : AppCompatActivity() {

    private lateinit var todoListAdapter: TodoListAdapter
    private val todoLists = TodoListsSingleton.SINGLETON_TODO_LISTS.todoLists

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_lists)
        todoListAdapter = TodoListAdapter()

        rv_list_container.adapter = todoListAdapter
        rv_list_container.layoutManager = LinearLayoutManager(this)

        button_create_list.setOnClickListener {
            val todoListTitle = et_list_title.text.toString()
            if(todoListTitle.isNotEmpty()) {
                val list = TodoList(todoListTitle, 0, mutableListOf())
                todoListAdapter.addList(list)
                et_list_title.text.clear()
            }
        }
    }
}