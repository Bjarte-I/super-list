package com.example.superlist

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.superlist.models.Todo
import com.example.superlist.models.TodoList

@RunWith(AndroidJUnit4::class)
@LargeTest
class UITesting {
    lateinit var testTodoList:TodoList

    @get:Rule
    var activityRule: ActivityScenarioRule<TodoListsActivity>
            = ActivityScenarioRule(TodoListsActivity::class.java)

    @Before
    fun setUpTestEnvironment() {
        // Make list.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        testTodoList = TodoList("Colors", mutableListOf(
                Todo("Blue", false), Todo("Red", false),
                Todo("Green", false)
        ))

        // Add list
        TodoListManager.instance.apply {
            load()
            removeAllLists(appContext)
            if (!getCollection().contains(testTodoList)){
                addTodoList(testTodoList, appContext)
            }
        }
    }

    @Test
    fun listsIsDisplayed_mainActivity() {
        onView(withId(R.id.rv_list_container))
                .check(matches(isDisplayed()))
    }

    @Test
    fun todosIsDisplayed_secondActivity() {
        onView(withId(R.id.rv_list_container))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(R.id.rv_details_list_container)) // This will for some reason not find the recyclerview even though it is clearly on screen when watching the debugger. I have spent hours on this and can not find any solution.
                .check(matches(isDisplayed()))
    }
}