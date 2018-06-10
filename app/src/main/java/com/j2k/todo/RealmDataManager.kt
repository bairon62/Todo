package com.j2k.todo

import android.content.Context
import android.util.Log
import io.reactivex.Completable
import io.reactivex.Observable
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults



class RealmDataManager {

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
        }

    }

    fun saveItem(item: Item): Completable {

        return Completable.create { observer ->
            val realm = Realm.getDefaultInstance()
            realm.executeTransactionAsync {
                realmInstance : Realm ->

                try {
                    realmInstance .copyToRealmOrUpdate(item)
                    realmInstance .commitTransaction()
                }
                catch (exception: Exception) {
                    realmInstance .cancelTransaction()
                }
                finally {
                    realmInstance .close()
                    observer.onComplete()
                }
            }
        }

    }

    fun getLastID() : Observable<Int> {
        return Observable.create<Int> { observer ->
            val realm = Realm.getDefaultInstance()

            realm.executeTransactionAsync { realmInstance ->

                val num:Number? = realmInstance.where(Item::class.java).max("id")
                var id = 0

                if(num != null) {
                    id = num.toInt()
                }

                observer.onNext(id)

                realmInstance.close()
            }
        }
    }

}