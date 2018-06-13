package com.j2k.todo

import android.databinding.Bindable
import android.databinding.Observable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Item: RealmObject(), Observable{
    @PrimaryKey
    @SerializedName("id")
    @Expose
    @Bindable
    open var id: Int = 0

    @SerializedName("content")
    @Expose
    @Bindable
    open var content: String = "content"

    @SerializedName("item_date")
    @Expose
    @Bindable
    open var date: String = "0"

    @SerializedName("isCompleted")
    @Expose
    @Bindable
    open var isCompleted : Boolean = false


    override fun addOnPropertyChangedCallback(p0: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(p0: Observable.OnPropertyChangedCallback?) {

    }
}