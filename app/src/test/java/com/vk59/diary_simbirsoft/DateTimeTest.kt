package com.vk59.diary_simbirsoft

import android.util.Log
import com.vk59.diary_simbirsoft.app.DiaryApplication
import com.vk59.diary_simbirsoft.repository.ServiceDB
import com.vk59.diary_simbirsoft.ui.creating.CreateViewModel
import io.realm.Realm
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import java.util.*


@RunWith(JUnit4::class)
class DateTimeTest {
    @Mock
    private lateinit var viewModel: CreateViewModel

    private lateinit var realm: Realm

    @Mock
    private var application = DiaryApplication()

    @Before
    fun setUp() {
        viewModel = CreateViewModel()
    }

    @Test
    fun translationDate() {
        val year = 2021
        val month = 7
        val day = 5
        val hour = 18
        val minute = 0
        val calendar = GregorianCalendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DAY_OF_MONTH, day - 1)
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)

        val expectedValue = calendar.timeInMillis

        viewModel.setChosenTime(18, 0)
        viewModel.setChosenDate(2021, 7, 5)

        val actualValue = ServiceDB
            .translateData(viewModel.dateStart.value!!, viewModel.timeStart.value!!)

        assertEquals("TEST DATE", calendar.time, actualValue.time)

        assertEquals("TEST DATE IN MILLISECONDS", expectedValue, actualValue.timeInMillis)
    }
}