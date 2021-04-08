package com.example.superlist

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.runner.intent.IntentStubberRegistry
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import com.example.superlist.util.getPickedTodoListIndex
import com.google.common.truth.Truth.assertThat

import org.junit.Test
import org.junit.runner.RunWith



/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class InstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertThat("com.example.superlist").isEqualTo(appContext.packageName)
    }

    @Test
    fun addTodoList_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", false)))
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            addTodoList(myTodoList, appContext)
        }
        assertThat(TodoListManager.instance.getCollection()).contains(myTodoList)
    }

    @Test
    fun removeTodoList_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", false)))
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            if(!(getCollection().contains(myTodoList))){
                addTodoList(myTodoList, appContext)
            }
            removeTodoList(myTodoList, appContext)
        }
        assertThat(TodoListManager.instance.getCollection()).doesNotContain(myTodoList)
    }

    @Test
    fun addTodo_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf())
        val myTodo = Todo("5", true)
        val listItemIndex:Int
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            if(!(getCollection().contains(myTodoList))) {
                addTodoList(myTodoList, appContext)
            }
            TodoListHolder.PickedTodoList = myTodoList
            addTodo(myTodo, appContext)
            listItemIndex = getPickedTodoListIndex()
        }
        assertThat(TodoListManager.instance.getCollection()[listItemIndex].listOfTodos).contains(myTodo)
    }

    @Test
    fun removeTodo_isCorrect() {
        val myTodo = Todo("5", true)
        val myTodoList = TodoList("Numbers", mutableListOf(myTodo))
        val listItemIndex:Int
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            if(!(getCollection().contains(myTodoList))) {
                addTodoList(myTodoList, appContext)
            }
            TodoListHolder.PickedTodoList = myTodoList
            listItemIndex = getPickedTodoListIndex()
            removeTodo(myTodo, appContext)
        }
        assertThat(TodoListManager.instance.getCollection()[listItemIndex].listOfTodos).doesNotContain(myTodo)
    }

    @Test
    fun removeAllLists_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf())
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            addTodoList(myTodoList, appContext)
            removeAllLists(appContext)
        }
        assertThat(TodoListManager.instance.getCollection().size == 0)
    }

    @Test
    fun updateTodo_isCorrect() {
        val myTodo = Todo("5", true)
        val expected = Todo("5", false)
        val myTodoList = TodoList("Numbers", mutableListOf(myTodo))
        val listItemIndex:Int
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TodoListManager.instance.apply {
            load()
            if(!(getCollection().contains(myTodoList))) {
                addTodoList(myTodoList, appContext)
            }
            TodoListHolder.PickedTodoList = myTodoList
            listItemIndex = getPickedTodoListIndex()
            updateTodo(myTodo, appContext)
        }
        assertThat(TodoListManager.instance.getCollection()[listItemIndex].listOfTodos).contains(expected)
    }

    @Test
    fun renameList_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", false)))
        val newTitle = "Nums"
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val listIndex:Int
        TodoListManager.instance.apply {
            if(!getCollection().contains(myTodoList)){
                addTodoList(myTodoList, appContext)
            }
            TodoListHolder.PickedTodoList = myTodoList
            renameList(newTitle, appContext)
            listIndex = getPickedTodoListIndex()
        }
        assertThat(TodoListManager.instance.getCollection()[listIndex].title).isEqualTo(newTitle)
    }
}