package com.vk59.diary_simbirsoft.repository

import android.util.Log
import com.vk59.diary_simbirsoft.model.Task
import com.vk59.diary_simbirsoft.model.TaskRealm
import io.realm.Realm
import java.util.*
import kotlin.collections.ArrayList


object DataBase {
    private var realm: Realm = Realm.getDefaultInstance()
    private val TAG = "DB"
    private var tasks = arrayListOf<Task>()

    fun saveData(name: String, dateStart: Long, dateFinish: Long, description: String) {
        realm.executeTransaction {
            val task = it.createObject(TaskRealm::class.java)
            val currentIdNum: Number? = realm.where(TaskRealm::class.java).max("id")
            task.id = (currentIdNum ?: 1L) as Long
            task.name = name
            task.dateStart = dateStart.toString()
            task.dateFinish = dateFinish.toString()
            task.description = description
        }
        Log.d(TAG,"On Success: Data Written Successfully!")
    }

    fun getAllData() : ArrayList<Task> {
        readData()
        return tasks
    }

    fun deleteAll() {
        realm.beginTransaction()
        realm.deleteAll()
        realm.commitTransaction()
    }

    private fun readData() {
        val realmTasks = realm.where(TaskRealm::class.java).findAll()
        var response=""
        tasks.clear()
        realmTasks.forEach {
            val task = Task(it)
            tasks.add(task)
            response = response + "Name: ${task.name}, description: ${task.description} " +
                    "date start: ${Date(task.dateStart.time)}, date finish: ${Date(task.dateFinish.time)}"+ "\n"
        }
        Log.d(TAG, response)
    }
}