package com.example.superlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_details_list.*
import kotlinx.android.synthetic.main.activity_main.*

class DetailsListActivity : AppCompatActivity() {

    private lateinit var detailsListAdapter: DetailsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_list)
        detailsListAdapter = DetailsListAdapter(mutableListOf())

        rv_details_list_container.adapter = detailsListAdapter
        rv_details_list_container.layoutManager = LinearLayoutManager(this)

        button_create_todo.setOnClickListener {
            val todoTitle = et_details_todo_title.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = DetailsTodoItem(todoTitle, false)
                detailsListAdapter.addTodo(todo)
                et_details_todo_title.text.clear()
            }
        }
    }
}