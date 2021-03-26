package com.example.superlist

import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList

class TodoListManager {
    private lateinit var todoListCollection: MutableList<TodoList>

    var onTodoLists: ((List<TodoList>) -> Unit)? = null
    var onTodos: ((List<Todo>) -> Unit)? = null
    var onTodoListUpdate: ((todoList: TodoList) -> Unit)? = null

    fun load() {
        if (!this::todoListCollection.isInitialized){
            todoListCollection = mutableListOf()
        }
        onTodoLists?.invoke(todoListCollection) // Update the displayed list.
    }

    fun updateTodoList(todoList: TodoList) {
        onTodoListUpdate?.invoke(todoList)
    }

    fun updateTodos(todoList: TodoList) {
        val currentTodoIndex = todoListCollection.indexOf(todoList)
        onTodos?.invoke(todoListCollection[currentTodoIndex].listOfTodos)
    }

    fun addTodoList(todoList: TodoList) {
        todoListCollection.add(todoList)
        onTodoLists?.invoke(todoListCollection)
    }

    fun removeTodoList(todoList: TodoList) {
        todoListCollection.remove(todoList)
        onTodoLists?.invoke(todoListCollection)
    }

    fun addTodo(todo: Todo, todoList: TodoList){
        val currentTodoIndex = todoListCollection.indexOf(todoList)
        todoListCollection[currentTodoIndex].listOfTodos.add(todo)
        onTodos?.invoke(todoListCollection[currentTodoIndex].listOfTodos)
    }

    fun removeTodo(todo: Todo, todoList: TodoList){
        val currentTodoIndex = todoListCollection.indexOf(todoList)
        todoListCollection[currentTodoIndex].listOfTodos.remove(todo)
        onTodos?.invoke(todoListCollection[currentTodoIndex].listOfTodos)
    }

    companion object {
        val instance = TodoListManager()
    }
}