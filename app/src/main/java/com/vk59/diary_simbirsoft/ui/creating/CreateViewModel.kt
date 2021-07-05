package com.vk59.diary_simbirsoft.ui.creating

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vk59.diary_simbirsoft.model.AppConfig
import com.vk59.diary_simbirsoft.repository.ServiceDB
import java.util.*
import kotlin.collections.ArrayList

class CreateViewModel : ViewModel() {
    var c = Calendar.getInstance()
    var dateStart = MutableLiveData(
        arrayListOf(
            toTimeString(c.get(Calendar.YEAR)),
            toTimeString(c.get(Calendar.MONTH)),
            toTimeString(c.get(Calendar.DAY_OF_MONTH))
        )
    )
    var timeStart = MutableLiveData<ArrayList<String>>()
    var dateFinish = MutableLiveData<ArrayList<String>>()
    var timeFinish = MutableLiveData<ArrayList<String>>()

    init {
        timeStart = MutableLiveData(
            arrayListOf(
                toTimeString(c.get(Calendar.HOUR_OF_DAY)),
                toTimeString(c.get(Calendar.MINUTE))
            )
        )
        val c2 = c
        c2.timeInMillis += AppConfig.taskDefaultDuration
        dateFinish = MutableLiveData(
            arrayListOf(
                toTimeString(c2.get(Calendar.YEAR)),
                toTimeString(c2.get(Calendar.MONTH)),
                toTimeString(c2.get(Calendar.DAY_OF_MONTH))
            )
        )

        timeFinish = MutableLiveData(
            arrayListOf(
                toTimeString(c2.get(Calendar.HOUR_OF_DAY)),
                toTimeString(c2.get(Calendar.MINUTE))
            )
        )
    }

    private fun toTimeString(num: Int): String {
        return (if (num < 10) "0" else "") + num.toString()
    }

    fun setChosenDate(year: Int, month: Int, day: Int) {
        dateStart.postValue(arrayListOf(year.toString(), toTimeString(month), toTimeString(day)))
    }


    fun setChosenTime(hour: Int, minute: Int) {
        timeStart.postValue(arrayListOf(toTimeString(hour), toTimeString(minute)))
    }

    fun setChosenDateFinish(year: Int, month: Int, day: Int) {
        dateFinish.postValue(arrayListOf(year.toString(), toTimeString(month), toTimeString(day)))
    }

    fun setChosenTimeFinish(hour: Int, minute: Int) {
        timeFinish.postValue(arrayListOf(toTimeString(hour), toTimeString(minute)))
    }

    fun saveData(name: String = "", description: String = "") {
        ServiceDB.saveData(
            name,
            dateStart.value!!,
            timeStart.value!!,
            dateFinish.value!!,
            timeFinish.value!!,
            description)
    }


}