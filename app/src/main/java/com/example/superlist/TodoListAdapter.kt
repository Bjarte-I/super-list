package com.example.superlist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.superlist.models.ListItem
import kotlinx.android.synthetic.main.item_todo_list.view.*

class TodoListAdapter : RecyclerView.Adapter<TodoListAdapter.ListViewHolder>() {
    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val listItems = ListItemsSingleton.singletonListItems.ListItems

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_todo_list, parent, false)
        )
    }

    fun addList(list: ListItem) {

        listItems.add(list)
        // lists.add(list)
        notifyItemInserted(listItems.size - 1)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.itemView.apply {
            tv_todo_title.text = listItems[position].title
        }


        holder.itemView.button_delete.setOnClickListener {
            listItems.removeAt(position)
            // listOfListItems.removeAt(position)
            notifyDataSetChanged()
        }

        holder.itemView.setOnClickListener {
            Intent(holder.itemView.context, DetailsListActivity::class.java).also {
                it.putExtra("EXTRA_LIST_ITEM_POSITION", position)
                startActivity(holder.itemView.context, it, null)
            }
        }
    }

    override fun getItemCount(): Int {
        return listItems.size
    }


}