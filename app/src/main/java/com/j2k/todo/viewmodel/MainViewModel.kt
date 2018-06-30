package com.j2k.todo.viewmodel

import android.content.Context
import com.j2k.todo.model.Item
import com.j2k.todo.model.realm.RealmDataManager
import com.j2k.todo.view.MainActivity
import com.j2k.todo.view.MainAdapter
import com.j2k.todo.view.ViewHolder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel {

    private var context: Context? = null
    private var realmDataManager: RealmDataManager? = null
    private var adapter: MainAdapter? = null

    fun init(context: Context, adapter: MainAdapter?) {
        this.context = context
        this.adapter = adapter
        realmDataManager = RealmDataManager()
        realmDataManager?.initRealm(context)
        loadItems()

    }

    fun onClickRegButton(text: String) {
        val item = Item()
        realmDataManager!!.getLastID()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    id ->
                    item.id = id
                    item.content = text
                    item.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

                    realmDataManager!!.saveItem(item)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                loadItems()
                            }

                }

    }

    fun onClickUpdateButton(item: Item, holder: ViewHolder) {
        (context as MainActivity).pd?.show()
        realmDataManager!!.updateItem(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    holder.updateGone()
                    loadItems()
                }
    }

    fun onClickDeleteButton(id:Int) {
        (context as MainActivity).pd?.show()
        realmDataManager!!.deleteItem(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadItems()
                }
    }

    fun onChecked(item: Item) {
        (context as MainActivity).pd?.show()
        realmDataManager!!.updateItem(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadItems()
                }
    }


    private fun loadItems() {
        realmDataManager!!.getAllItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    items -> updateView(items)
                    realmDataManager?.realm?.close()
                    (context as MainActivity).pd?.dismiss()
                }
    }

    private fun updateView(items:List<Item>) {
        adapter?.data = items as MutableList<Item>
        adapter?.notifyDataSetChanged()
    }

}