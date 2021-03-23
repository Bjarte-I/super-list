package com.example.superlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.models.Todo
import kotlinx.android.synthetic.main.activity_todo_details.*

class TodoDetailsActivity : AppCompatActivity() {

    private lateinit var todoDetailsAdapter: TodoDetailsAdapter
    private val todoLists = TodoListsSingleton.SINGLETON_TODO_LISTS.todoLists
    //All the lists that are added and stored in the singleton object.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_details)
        val listPosition:Int = intent.getIntExtra("EXTRA_LIST_POSITION", -1)
        //Retrieve the index position of the list we are in.

        todoDetailsAdapter = TodoDetailsAdapter(listPosition)

        rv_details_list_container.adapter = todoDetailsAdapter
        rv_details_list_container.layoutManager = LinearLayoutManager(this)

        tv_todo_list_title.text = todoLists[listPosition].title

        button_details_back.setOnClickListener {
            Intent(this, TodoListsActivity::class.java).also {
                ContextCompat.startActivity(this, it, null)
                //Start the main activity
            }
        }

        button_create_todo.setOnClickListener {
            val todoTitle = et_details_todo_title.text.toString()
            if(todoTitle.isNotEmpty()) {
                val todo = Todo(todoTitle, false)
                todoDetailsAdapter.addTodo(todo)
                //Add the todos through the recycler adapter
                et_details_todo_title.text.clear()
            }
        }

    }
}