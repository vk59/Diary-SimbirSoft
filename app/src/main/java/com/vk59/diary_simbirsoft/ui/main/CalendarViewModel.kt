package com.vk59.diary_simbirsoft.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import com.applandeo.materialcalendarview.EventDay
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.model.ConfigExample
import com.vk59.diary_simbirsoft.model.Task
import com.vk59.diary_simbirsoft.model.TaskJson
import java.util.*

class CalendarViewModel : ViewModel() {
    var events = arrayListOf<EventDay>()
    var tasks = arrayListOf<Task>()

    fun getDataConfig() {
        tasks.clear()
        val config = ConfigExample.tasks

        for (t: TaskJson in config) {
            tasks.add(Task(t))
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = Task(t).dateStart.time
            Log.d("DATE", calendar.time.toString())
            events.add(EventDay(calendar, R.drawable.icon_task))
        }
    }


}