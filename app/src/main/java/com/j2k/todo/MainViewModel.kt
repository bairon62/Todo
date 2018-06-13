package com.j2k.todo

import android.content.Context
import android.util.Log
import com.j2k.todo.view.MainActivity
import com.j2k.todo.view.MainAdapter
import com.j2k.todo.view.ViewHolder
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel {

    var context: Context? = null
    var realmDataManager: RealmDataManager? = null
    var adapter: MainAdapter? = null

    fun init(context: Context, adapter: MainAdapter?) {
        this.context = context
        this.adapter = adapter
        realmDataManager = RealmDataManager()
        realmDataManager?.initRealm(context)
        loadItems()


    }

    fun onClickRegButton(text: String) {
        var item :Item = Item()
        realmDataManager!!.getLastID()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    id ->
                    item?.id = id
                    item?.content = text
                    item?.date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())

                    realmDataManager!!.saveItem(item)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                loadItems()
                            }

                }

    }

    fun onClickUpdateButton(item:Item, holder: ViewHolder) {
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

    fun onChecked(item:Item) {
        (context as MainActivity).pd?.show()
        realmDataManager!!.updateItem(item)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    loadItems()
                }
    }


    fun loadItems() {
        realmDataManager!!.getAllItems()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    items -> updateView(items)
                    realmDataManager?.realm?.close()
                    (context as MainActivity).pd?.dismiss()
                }
    }

    fun updateView(items:List<Item>) {
        adapter?.data = items as MutableList<Item>
        adapter?.notifyDataSetChanged()
    }

}