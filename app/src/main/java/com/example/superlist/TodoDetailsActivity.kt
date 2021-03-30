package com.example.superlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.superlist.databinding.ActivityTodoDetailsBinding
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import com.example.superlist.util.getPickedTodoList

class TodoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoDetailsBinding
    private lateinit var todoList: TodoList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TodoListManager.instance.load()

        todoList = getPickedTodoList()

        binding.rvDetailsListContainer.adapter = TodoDetailsAdapter(todoList.listOfTodos, this::onCheckboxChanged, this::onDeleteClicked) //List that is displayed from the start
        binding.rvDetailsListContainer.layoutManager = LinearLayoutManager(this)
        binding.tvTodoListTitle.text = todoList.title

        TodoListManager.instance.onTodos = {
            (binding.rvDetailsListContainer.adapter as TodoDetailsAdapter).updateCollection(it)
        }

        updateListProgressBar()

        binding.buttonDetailsBack.setOnClickListener {
            val intent = Intent(this, TodoListsActivity::class.java)
            startActivity(intent)
            //Start the main activity
        }

        binding.buttonCreateTodo.setOnClickListener {
            val todoTitle = binding.etDetailsTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                addTodo(todoTitle)
                binding.etDetailsTodoTitle.text.clear()
                updateListProgressBar()
            }
        }
    }

    private fun onDeleteClicked(todo: Todo) {
        TodoListManager.instance.removeTodo(todo, binding.root.context)
        updateListProgressBar()
    }

    private fun onCheckboxChanged(todo: Todo) {
        TodoListManager.instance.updateTodo(todo, binding.root.context)
            updateListProgressBar()
    }

    private fun addTodo(title: String) {
        val newTodo = Todo(title, false)
        TodoListManager.instance.addTodo(newTodo, this)
    }

    private fun updateListProgressBar() {
        val progress: Int = TodoListManager.instance.calculateListProgress(getPickedTodoList())
        binding.pbDetailsProgress.progress = progress
    }
}