package com.example.superlist

import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList
import com.example.superlist.util.getPickedTodoList
import com.google.common.truth.Truth.assertThat
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class UnitTest {
    @Test
    fun getPickedTodoList_isCorrect() {
        val myTodoList = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", false)))
        TodoListHolder.PickedTodoList = myTodoList
        assertThat(getPickedTodoList()).isEqualTo(myTodoList)
    }

    @Test
    fun calculateListProgress_isCorrect() {
        val myTodoList1 = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", false)))
        val myTodoList2 = TodoList("Numbers", mutableListOf(Todo("1", true), Todo("2", true), Todo("3", false)))
        val myTodoList3 = TodoList("Numbers", mutableListOf(Todo("1", false), Todo("2", true), Todo("3", false)))

        val expected1 = 50
        val expected2 = 66
        val expected3 = 33

        assertThat(TodoListManager.instance.calculateListProgress(myTodoList1)).isEqualTo(expected1)
        assertThat(TodoListManager.instance.calculateListProgress(myTodoList2)).isEqualTo(expected2)
        assertThat(TodoListManager.instance.calculateListProgress(myTodoList3)).isEqualTo(expected3)
    }
}