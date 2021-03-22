package com.example.superlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.models.ListItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {

    private lateinit var todoListAdapter: TodoListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        todoListAdapter = TodoListAdapter()

        rv_list_container.adapter = todoListAdapter
        rv_list_container.layoutManager = LinearLayoutManager(this)

        button_create_list.setOnClickListener {
            val listTitle = et_todo_list_title.text.toString()
            if(listTitle.isNotEmpty()) {
                val list = ListItem(listTitle, mutableListOf())
                todoListAdapter.addList(list)
                et_todo_list_title.text.clear()
            }
        }

    }
}