package com.example.superlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.databinding.ActivityMainBinding
import com.example.superlist.models.TodoList

class TodoListHolder {
    companion object{
        var PickedTodoList:TodoList? = null
    }
}

class TodoListsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvListContainer.layoutManager = LinearLayoutManager(this)
        TodoListManager.instance.load()
        val todoLists = TodoListManager.instance.getCollection()
        binding.rvListContainer.adapter = TodoListAdapter(todoLists, this::onTodoListClicked)

        TodoListManager.instance.onTodoLists = {
            (binding.rvListContainer.adapter as TodoListAdapter).updateCollection(it)
        }

        binding.buttonCreateList.setOnClickListener {
            val todoListTitle = binding.etListTitle.text.toString()
            if(todoListTitle.isNotEmpty()) {
                addTodoList(todoListTitle)
                binding.etListTitle.setText("")
                val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

    private fun addTodoList(title:String) {
        val newTodoList = TodoList(title, mutableListOf())
        TodoListManager.instance.addTodoList(newTodoList, this)
    }

    private fun onTodoListClicked(todoList: TodoList) {
        TodoListHolder.PickedTodoList = todoList

        val intent = Intent(this, TodoDetailsActivity::class.java)

        startActivity(intent)
    }
}