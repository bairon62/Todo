package com.j2k.todo.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.j2k.todo.MainViewModel
import com.j2k.todo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewModel:MainViewModel? = null
    var adapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initList()
        viewModel =  MainViewModel()
        viewModel?.init(this)



        reg_button.setOnClickListener {
            viewModel?.onClickRegButton(editText.text.toString())
        }


    }

    private fun initList() {
        listView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this)
        listView.adapter = adapter
    }




}
