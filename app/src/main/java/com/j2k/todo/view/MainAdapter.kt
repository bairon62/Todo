package com.j2k.todo.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.j2k.todo.Item
import com.j2k.todo.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView : TextView = itemView.findViewById(R.id.textView)

}

class MainAdapter(val context: Context): RecyclerView.Adapter<ViewHolder>() {

    val data = mutableListOf<Item>()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainView = inflater.inflate(R.layout.list_item, parent, false)
        var viewHolder = ViewHolder(mainView)
        return viewHolder
    }


}