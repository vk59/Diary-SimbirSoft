package com.vk59.diary_simbirsoft.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Task (
    val id: Long,
    var dateStart: Date,
    var dateFinish: Date,
    var name: String,
    var description: String) : Parcelable {

    constructor(task: TaskJson) :
            this(task.id, Date(task.dateStart.toLong() * 1000),
                Date(task.dateFinish.toLong() * 1000),
                task.name,
                task.description)
    constructor(task: TaskRealm) :
            this(task.id!!,
                Date(task.dateStart!!.toLong() * 1000),
                Date(task.dateFinish!!.toLong() * 1000),
                task.name!!,
                task.description!!)

}