package com.example.superlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.models.DetailsTodoItem
import com.example.superlist.models.ListItem
import com.example.superlist.models.ListItems
import kotlinx.android.synthetic.main.activity_details_list.*
import kotlinx.android.synthetic.main.activity_details_list.view.*

class DetailsListActivity : AppCompatActivity() {

    private lateinit var detailsListAdapter: DetailsListAdapter
    private val listItems = ListItemsSingleton.singletonListItems.ListItems

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_list)
        val listPosition:Int = intent.getIntExtra("EXTRA_LIST_ITEM_POSITION", -1)

        detailsListAdapter = DetailsListAdapter(listPosition)

        rv_details_list_container.adapter = detailsListAdapter
        rv_details_list_container.layoutManager = LinearLayoutManager(this)

        button_details_back.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                ContextCompat.startActivity(this, it, null)
            }
        }

        button_create_todo.setOnClickListener {
            val todoTitle = et_details_todo_title.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = DetailsTodoItem(todoTitle, false)
                detailsListAdapter.addTodo(todo)
                et_details_todo_title.text.clear()
            }
        }

        tv_todo_list_title.text = listItems[listPosition].title
    }
}