package com.vk59.diary_simbirsoft.repository

import java.util.*
import kotlin.collections.ArrayList

object ServiceDB {
    fun saveData(
        name: String,
        dateStart: ArrayList<String>,
        timeStart: ArrayList<String>,
        dateFinish: ArrayList<String>,
        timeFinish: ArrayList<String>,
        description: String
    ) {
        val calendarStart = translateData(dateStart, timeStart)
        var dateStart = calendarStart.timeInMillis

        val calendarFinish = translateData(dateFinish, timeFinish)
        var dateFinish = calendarFinish.timeInMillis

        dateStart /= 1000
        dateFinish /= 1000

        DataBase.saveData(name, dateStart, dateFinish, description)
    }

    fun translateData(
        date: ArrayList<String>,
        time: ArrayList<String>,
    ): Calendar {
        val calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.YEAR, date[0].toInt())
        calendar.set(Calendar.MONTH, date[1].toInt())
        calendar.set(Calendar.DAY_OF_MONTH, date[2].toInt())
        calendar.set(Calendar.HOUR_OF_DAY, time[0].toInt())
        calendar.set(Calendar.MINUTE, time[1].toInt())
        return calendar
    }

}