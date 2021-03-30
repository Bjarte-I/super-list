package com.example.superlist

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import com.example.superlist.util.getPickedTodoList
import com.example.superlist.util.getPickedTodoListIndex
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.File
import java.io.FileOutputStream

class TodoListManager {
    private lateinit var todoListCollection: MutableList<TodoList>
    private lateinit var auth: FirebaseAuth

    var onTodoLists: ((List<TodoList>) -> Unit)? = null
    var onTodos: ((List<Todo>) -> Unit)? = null

    fun load() {
        todoListCollection = mutableListOf()
            auth = FirebaseAuth.getInstance()
            signInAnonymously()

            val ref = FirebaseStorage.getInstance().reference.child("Lists/TodoLists.json")
            val localFile = File.createTempFile("lists", "json")
            ref.getFile(localFile).addOnSuccessListener {
                val content = localFile.readText()
                Log.d("MANAGER", "the string: $content")
                val sType = object : TypeToken<MutableList<TodoList>>() { }.type
                val downloadedTodoListsCollection:MutableList<TodoList> = Gson().fromJson(content, sType)
                for(todoList in downloadedTodoListsCollection) {
                    todoListCollection.add(todoList)
                    onTodoLists?.invoke(todoListCollection) // Update the collection.
                }
            }.addOnFailureListener {
                Log.d("MANAGER", "Failed to download file: $it")
            }
    }

    private fun signInAnonymously() {
        auth.signInAnonymously().addOnSuccessListener {
            Log.d("TAG", "Login success ${it.user.toString()}")
        }.addOnFailureListener {
            Log.e("TAG", "Logging in failed", it)
        }
    }

    private fun updateDatabase(context: Context) {
        val content = Gson().toJson(todoListCollection)

        val path = context.getExternalFilesDir(null)
        if(path != null) {
            val myFile = File(path, "TodoLists.json")
            FileOutputStream(myFile, false).bufferedWriter().use { writer ->
                writer.write(content)
                println("File saved")
            }
            val file:Uri = myFile.toUri()
            Log.d("MANAGER", "$file")
            val ref = FirebaseStorage.getInstance().reference.child("Lists/${file.lastPathSegment}")
            ref.putFile(file)
        }
    }

    fun addTodoList(todoList: TodoList, context: Context) {
        todoListCollection.add(todoList)
        updateDatabase(context)
        onTodoLists?.invoke(todoListCollection)
    }

    fun removeTodoList(todoList: TodoList, context: Context) {
        todoListCollection.remove(todoList)
        updateDatabase(context)
        onTodoLists?.invoke(todoListCollection)
    }

    fun addTodo(todo: Todo, context: Context) {
        val todoListIndex = getPickedTodoListIndex()
        todoListCollection[todoListIndex].listOfTodos.add(todo)
        TodoListHolder.PickedTodoList = todoListCollection[todoListIndex] //Update the pickedTodo to include the newest version. If not updated, will cause out of bound with the indexOf method.
        updateDatabase(context)
        onTodos?.invoke(todoListCollection[todoListIndex].listOfTodos)
    }

    fun getCollection(): MutableList<TodoList> {
        return todoListCollection
    }

    fun updateTodo(todo:Todo, context: Context) {
        val index = getPickedTodoListIndex()
        val todoIndex = todoListCollection[index].listOfTodos.indexOf(todo)
        if(todoIndex == -1) {
            Log.d("MANAGER - UPDATE TODO", "Index is out of bounds")
            return
        }
        val todoInCollection:Todo = todoListCollection[index].listOfTodos[todoIndex]
        todoInCollection.isChecked = !todoInCollection.isChecked //Update both the collection and the individual _todo
        todo.isChecked = todoInCollection.isChecked
        updateDatabase(context)
        TodoListHolder.PickedTodoList = todoListCollection[index] //TODO: crashes when changing checkbox and then removing that todo
        Log.d("MANAGER", "running")
    }

    fun removeTodo(todo: Todo, context:Context) {
        val index = getPickedTodoListIndex()
        todoListCollection[index].listOfTodos.remove(todo)
        updateDatabase(context)
        onTodos?.invoke(todoListCollection[index].listOfTodos)
        TodoListHolder.PickedTodoList = todoListCollection[index]
    }

    fun calculateListProgress(todoList: TodoList) : Int {
        var isCheckedCount = 0
        for(todo in todoList.listOfTodos){
            if(todo.isChecked) {
                isCheckedCount++
            }
        }
        return (100.0 * isCheckedCount / todoList.listOfTodos.size).toInt()
    }

    companion object {
        val instance = TodoListManager()
    }
}