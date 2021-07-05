package com.vk59.diary_simbirsoft.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.model.AppConfig
import com.vk59.diary_simbirsoft.model.Task
import com.vk59.diary_simbirsoft.model.TaskJson
import com.vk59.diary_simbirsoft.repository.DataBase
import java.util.*
import kotlin.collections.ArrayList

class CalendarViewModel() : ViewModel() {
    var events = arrayListOf<EventDay>()
    var tasks = arrayListOf<Task>()

    private var currentDate: MutableLiveData<Date>
    private var currentDateStart: Long
    private var currentDateFinish: Long

    var currentTasks = mutableListOf<ArrayList<Task>>()

    init {
        val c = Calendar.getInstance()
        c.timeInMillis = c.timeInMillis - c.timeInMillis % AppConfig.dayInMillis
//        Log.d("CURRENT TIME: ", c.time.toString())
        currentDate = MutableLiveData(c.time)
        currentDateStart = c.timeInMillis
        currentDateFinish = currentDateStart + AppConfig.dayInMillis
        for (i: Int in 0..23) {
            currentTasks.add(ArrayList<Task>())
        }

        currentDate.observeForever {
            findTasksThatDay()
            Log.d("CURRENT TASKS", Date(currentDateStart).toString() +" " + currentTasks.toString())
        }
    }

    private fun findTasksThatDay() {
        Log.d("FIND TASK DAY", Date(currentDateStart).toString())
        var timeHourStart = currentDateStart
        var timeHourFinish = currentDateStart + AppConfig.hourInMills
        currentTasks.forEach {
            it.clear()
            for (task in tasks) {
                if (taskNow(timeHourStart, timeHourFinish, task.dateStart.time, task.dateFinish.time)) {
                    it.add(task)
                } else {
                    Log.d("FIND TASK DAY", "FALSE " +
                            "${Date(timeHourStart).hours} in ${task.dateStart.hours} until ${task.dateFinish.hours} ||\n" +
                            "                    ${Date(timeHourFinish).hours} in ${task.dateStart.hours} until " +
                            "${task.dateFinish.hours}")
                }
            }
            timeHourStart = timeHourFinish
            timeHourFinish += AppConfig.hourInMills
        }
    }

    private fun taskNow(timeHourStart: Long, timeHourFinish: Long, taskStartTime: Long, taskFinishTime: Long): Boolean {
        return ((timeHourStart in taskStartTime until taskFinishTime ||
                timeHourFinish in taskStartTime until taskFinishTime) && taskStartTime != timeHourFinish
                || taskStartTime in timeHourStart until timeHourFinish
                && taskFinishTime in timeHourStart until timeHourFinish)
    }

    fun getDataConfig() {
        tasks.clear()
        val config = AppConfig.tasks

        for (t: TaskJson in config) {
            tasks.add(Task(t))
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = Task(t).dateStart.time
//            Log.d("DATE", calendar.time.toString())
            events.add(EventDay(calendar, R.drawable.icon_task))
        }
//        Log.d("VIEW MODEL", tasks.toString())
    }

    fun getData() {
        tasks = DataBase.getAllData()
        events.clear()
        tasks.forEach {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = it.dateStart.time
            events.add(EventDay(calendar, R.drawable.icon_task))
        }
    }

    fun setCurrentDate(c: Calendar) {
        val curDate = c.timeInMillis - c.timeInMillis % AppConfig.dayInMillis
        if (!currentDate.equals(Date(curDate))) {
            currentDate.postValue(Date(curDate))
            currentDateStart = c.timeInMillis
            currentDateFinish = currentDateStart + AppConfig.dayInMillis
        }
    }

}