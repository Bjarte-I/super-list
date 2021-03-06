package com.example.superlist

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Switch
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.databinding.ActivityTodoDetailsBinding
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import com.example.superlist.util.getPickedTodoList
import com.example.superlist.util.getPickedTodoListIndex
import java.util.*

class TodoDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTodoDetailsBinding
    private lateinit var todoList: TodoList
    private lateinit var adapter: TodoDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTodoDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        TodoListManager.instance.load()

        todoList = getPickedTodoList()

        adapter = TodoDetailsAdapter(todoList.listOfTodos, this::onCheckboxChanged, this::onDeleteClicked)
        binding.rvDetailsListContainer.adapter = adapter
        binding.rvDetailsListContainer.layoutManager = LinearLayoutManager(this)
        binding.etTodoListTitle.setText(todoList.title)

        TodoListManager.instance.onTodos = {
            (binding.rvDetailsListContainer.adapter as TodoDetailsAdapter).updateCollection(it)
        }

        updateListProgressBar()

        binding.buttonDetailsBack.setOnClickListener {
            val intent = Intent(this, TodoListsActivity::class.java)
            startActivity(intent)
            //Start the main activity
        }

        binding.buttonRenameList.setOnClickListener {
            val newListTitle:String = binding.etTodoListTitle.text.toString()
            if(newListTitle != "" && newListTitle != todoList.title) {
                TodoListManager.instance.renameList(newListTitle, this)
                val toast = Toast.makeText(applicationContext, "Changed the list name", Toast.LENGTH_LONG)
                val ipm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                ipm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                toast.show()
            }
        }

        binding.buttonCreateTodo.setOnClickListener {
            val todoTitle = binding.etDetailsTodoTitle.text.toString()
            if(todoTitle.isNotEmpty()) {
                addTodo(todoTitle)
                binding.etDetailsTodoTitle.text.clear()
                updateListProgressBar()
            }
        }

        val touchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = viewHolder.adapterPosition
                val targetPosition = target.adapterPosition
                val currentIndex = getPickedTodoListIndex()
                TodoListManager.instance.apply {
                    Collections.swap(getCollection()[currentIndex]
                        .listOfTodos, sourcePosition, targetPosition)
                    updateTodoSequence(currentIndex, this@TodoDetailsActivity)
                }
                adapter.notifyItemMoved(sourcePosition, targetPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if(direction == ItemTouchHelper.RIGHT) {
                    TodoListManager.instance.removeTodoAt(viewHolder.adapterPosition, this@TodoDetailsActivity)
                    adapter.notifyItemRemoved(viewHolder.adapterPosition)
                    updateListProgressBar()
                }
            }

        })

        touchHelper.attachToRecyclerView(binding.rvDetailsListContainer)

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