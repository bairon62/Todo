package com.j2k.todo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Item: RealmObject(){
    @PrimaryKey
    @SerializedName("id")
    @Expose
    open var id: Int = 0

    @SerializedName("content")
    @Expose
    open var content: String = "content"

    @SerializedName("item_date")
    @Expose
    open var date: String = "0"

    @SerializedName("isCompleted")
    @Expose
    open var isCompleted : Boolean = false
}