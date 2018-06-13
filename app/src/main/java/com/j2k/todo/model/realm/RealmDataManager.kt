package com.j2k.todo.model.realm

import android.content.Context
import android.util.Log
import com.j2k.todo.model.Item
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration


class RealmDataManager {

    var realm:Realm? = null

    fun initRealm(context: Context) {
        Realm.init(context)
        val config =  RealmConfiguration.Builder().build()
        Realm.setDefaultConfiguration(config)
    }

    fun getAllItems(): Observable<List<Item>> {
        return Observable.create<List<Item>> { observer ->
            val realm = Realm.getDefaultInstance()

            realm.executeTransactionAsync { realmInstance ->
                val result = realmInstance.where(Item::class.java).findAll()
                val list:List<Item> = realmInstance.copyFromRealm(result)

                observer.onNext(list)

                realmInstance.close()
            }
            realm.close()
        }

    }

    fun saveItem(item: Item): Observable<Any> {
        return Observable.create { observer ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransactionAsync {
                realmInstance : Realm ->

                try {
                    realmInstance .copyToRealm(item)
                    realmInstance .commitTransaction()
                }
                catch (exception: Exception) {
                    Log.d("realm", exception.toString())
                    realmInstance .cancelTransaction()
                }
                finally {
                    realmInstance .close()
                    observer.onNext(item)
                }
            }
            realm.close()
        }

    }

    fun getLastID() : Observable<Int> {
        return Observable.create<Int> { observer ->
            val realm = Realm.getDefaultInstance()

            realm.executeTransactionAsync { realmInstance ->

                val num:Number? = realmInstance.where(Item::class.java).max("id")
                var id = 0

                if(num != null) {
                    id = num.toInt() + 1
                }

                observer.onNext(id)

                realmInstance.close()
            }
            realm.close()
        }
    }
    fun deleteItem(id:Int): Observable<Any> {
        return Observable.create {
            observer ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransactionAsync {
                realmInstance : Realm ->
                try {
                    realmInstance .where(Item::class.java)
                            .equalTo("id", id).findFirst()!!.deleteFromRealm()
                    realmInstance .commitTransaction()
                }
                catch (exception: Exception) {
                    realmInstance .cancelTransaction()
                }
                finally {
                    realmInstance .close()
                    observer.onNext(id)
                }
            }
            realm.close()
        }
    }
    fun updateItem(item: Item): Observable<Any> {
        return Observable.create {
            observer ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransactionAsync {
                realmInstance : Realm ->
                try {
                    realmInstance.copyToRealmOrUpdate(item)
                    realmInstance.commitTransaction()
                }
                catch (exception: Exception) {
                    realmInstance.cancelTransaction()
                }
                finally {
                    realmInstance.close()
                    observer.onNext(item)
                }
            }
            realm.close()
        }
    }

}