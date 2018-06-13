package com.j2k.todo.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.j2k.todo.BR
import com.j2k.todo.Item
import com.j2k.todo.MainViewModel
import com.j2k.todo.R
import com.j2k.todo.databinding.ListItemBinding
import java.text.SimpleDateFormat
import java.util.*

class ViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Item) {
        binding.setVariable(BR.item, item)
    }

    fun updateVisible() {
        binding.btnUpdate.visibility = View.GONE
        binding.btnDelete.visibility = View.GONE
        binding.linearlayout.visibility = View.GONE
        binding.edit.visibility = View.VISIBLE
        binding.btnConfirm.visibility = View.VISIBLE
    }
    fun updateGone() {
        binding.btnUpdate.visibility = View.VISIBLE
        binding.btnDelete.visibility = View.VISIBLE
        binding.linearlayout.visibility = View.VISIBLE
        binding.edit.visibility = View.GONE
        binding.btnConfirm.visibility = View.GONE
    }
}

class MainAdapter(val context: Context): RecyclerView.Adapter<ViewHolder>() {

    var data = mutableListOf<Item>()
    var viewModel: MainViewModel? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item: Item = data[position]
        holder.bind(item)
        holder.binding.btnDelete.setOnClickListener {
            viewModel?.onClickDeleteButton(item.id)
        }
        holder.binding.btnUpdate.setOnClickListener {
            holder.updateVisible()

        }
        holder.binding.btnConfirm.setOnClickListener {
            item.content = holder.binding.edit.text.toString()
            item.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
            viewModel?.onClickUpdateButton(item, holder)
        }
        holder.binding.checkBox.setOnCheckedChangeListener {
            compoundButton, isChecked ->
            item.isCompleted = isChecked
            viewModel?.onChecked(item)
        }

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var binding:ListItemBinding = ListItemBinding.inflate(inflater, parent, false)
        val mainView = inflater.inflate(R.layout.list_item, parent, false)
        var viewHolder = ViewHolder(binding)
        return viewHolder
    }

    fun initViewModel(viewModel: MainViewModel?) {
        this.viewModel = viewModel
    }

}