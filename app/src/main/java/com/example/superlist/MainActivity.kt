package com.example.superlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_todo_list.*
import kotlinx.android.synthetic.main.item_todo_list.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoListAdapter = TodoListAdapter(mutableListOf())

        rv_list_container.adapter = todoListAdapter
        rv_list_container.layoutManager = LinearLayoutManager(this)

        button_create_list.setOnClickListener {
            val listTitle = et_todo_list_title.text.toString()
            if(listTitle.isNotEmpty()) {
                val list = ListItem(listTitle)
                todoListAdapter.addList(list)
                et_todo_list_title.text.clear()
            }
        }

        button_delete.setOnClickListener {
            todoListAdapter.deleteList()
        }
    }
}