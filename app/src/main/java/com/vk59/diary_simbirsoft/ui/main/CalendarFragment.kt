package com.vk59.diary_simbirsoft.ui.main

import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.applandeo.materialcalendarview.CalendarView
import com.applandeo.materialcalendarview.DatePicker
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.builders.DatePickerBuilder
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.listeners.OnSelectDateListener
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.databinding.CalendarFragmentBinding
import com.vk59.diary_simbirsoft.model.Task
import com.vk59.diary_simbirsoft.ui.main.recycler_view.TodayRecyclerAdapter
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {

    private lateinit var adapter: TodayRecyclerAdapter

    private var events: List<EventDay>? = null
    private lateinit var calendarView: CalendarView
    private lateinit var todayRecyclerView: RecyclerView

    private lateinit var binding: CalendarFragmentBinding

    private lateinit var viewModel: CalendarViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.calendar_fragment, container, false)
        binding = CalendarFragmentBinding.bind(view)
        calendarView = binding.calendarView
        todayRecyclerView = binding.todayRecyclerView
        binding.addButton.setOnClickListener { onAddListener() }
        return binding.root
    }

    private fun onAddListener() {
        findNavController().navigate(R.id.action_calendarFragment_to_createFragment)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)
//        viewModel.getDataConfig()
        viewModel.getData()
        events = viewModel.events

        initCalendarView()
        initRecyclerView()
    }

    private fun initCalendarView() {
        val min = Calendar.getInstance()
        min.add(Calendar.MONTH, -12)

        val max = Calendar.getInstance()
        max.add(Calendar.MONTH, 12)

        calendarView.setMinimumDate(min)
        calendarView.setMaximumDate(max)

        calendarView.setEvents(events)

        calendarView.setOnDayClickListener(listener)
    }

    var listener = OnDayClickListener { event: EventDay ->
        val c = event.calendar
        viewModel.setCurrentDate(c)
        adapter.setTasks(viewModel.currentTasks)
        binding.chosenDate.text =
            "Chosen date: ${c.get(Calendar.DAY_OF_MONTH)}.${c.get(Calendar.MONTH)}.${c.get(Calendar.YEAR)}"
    }

    private fun initRecyclerView() {
        todayRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = TodayRecyclerAdapter(requireContext())
        adapter.setTasks(tasks = viewModel.currentTasks)
        todayRecyclerView.adapter = adapter
    }
}