package com.j2k.todo.view

import android.app.ProgressDialog
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.j2k.todo.MainViewModel
import com.j2k.todo.R
import com.j2k.todo.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var viewModel:MainViewModel? = null
    var adapter: MainAdapter? = null
    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding:ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this

        pd = ProgressDialog.show(this, "처리중", "처리중")
        viewModel =  MainViewModel()
        initList()
        viewModel?.init(this, adapter)
        adapter?.initViewModel(viewModel)



    }

    private fun initList() {
        listView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this)
        listView.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun regButtonClick(view: View) {
        viewModel?.onClickRegButton(editText.text.toString())
    }



}
