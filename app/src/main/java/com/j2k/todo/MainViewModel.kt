package com.j2k.todo

import android.content.Context
import android.util.Log
import com.j2k.todo.view.MainAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel {

    private var items: List<Item>? = null
    var context: Context? = null
    var realmDataManager: RealmDataManager? = null
    var adapter: MainAdapter? = null

    fun init(context: Context) {
        this.context = context
        realmDataManager = RealmDataManager()
        realmDataManager?.initRealm(context)
        loadItems()


    }

    fun onClickRegButton(text: String) {

        realmDataManager!!.saveItem(createItem(text))
    }

    fun onClickUpdateButton() {

    }

    fun onClickDeleteButton() {

    }

    fun onChecked() {

    }

    fun onUnchecked() {

    }

    fun loadItems() {
        realmDataManager!!.getAllItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items -> updateView(items) }
    }

    fun updateView(items:List<Item>) {

    }

    fun createItem(text: String): Item {
        var item = Item()

        realmDataManager!!.getLastID()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    item.id = it
                    item.content = text
                    item.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

                }

        return item

    }


}