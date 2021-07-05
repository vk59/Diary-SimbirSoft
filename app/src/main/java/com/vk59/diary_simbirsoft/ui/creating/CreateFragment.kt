package com.vk59.diary_simbirsoft.ui.creating

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.databinding.CreateFragmentBinding

class CreateFragment : Fragment() {

    private lateinit var viewModel: CreateViewModel
    private lateinit var binding: CreateFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.create_fragment, container, false)
        binding = CreateFragmentBinding.bind(view)
        setClickListeners()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateViewModel::class.java)

        observeDateChoosing()
    }

    private fun setClickListeners() {
        binding.dateText.setOnClickListener { setDateStart() }
        binding.timeText.setOnClickListener { setTimeStart() }
        binding.dateTextFinish.setOnClickListener { setDateFinish() }
        binding.timeTextFinish.setOnClickListener { setTimeFinish() }
        binding.addTaskButton.setOnClickListener {
            val name = binding.inputTaskName.text.toString()
            val description = binding.inputDescription.text.toString()
            viewModel.saveData(name, description)
            findNavController().navigate(R.id.action_createFragment_to_calendarFragment)
        }
    }

    private fun observeDateChoosing() {
        viewModel.dateStart.observe(viewLifecycleOwner, {
            binding.dateText.text = "${it[2]}.${it[1]}.${it[0]}"
        })
        viewModel.timeStart.observe(viewLifecycleOwner, {
            binding.timeText.text = "${it[0]}:${it[1]}"
        })
        viewModel.dateFinish.observe(viewLifecycleOwner, {
            binding.dateTextFinish.text = "${it[2]}.${it[1]}.${it[0]}"
        })
        viewModel.timeFinish.observe(viewLifecycleOwner, {
            binding.timeTextFinish.text = "${it[0]}:${it[1]}"
        })
    }

    private fun setDateStart() {
        val date = viewModel.dateStart.value
        DatePickerDialog(
            requireView().context, onDateStartSetListener,
            date!![0].toInt(), date[1].toInt(), date[2].toInt()
        ).show()

    }

    private fun setTimeStart() {
        val time = viewModel.timeStart.value
        TimePickerDialog(
            requireView().context, onTimeStartSetListener,
            time!![0].toInt(), time[1].toInt(), true
        ).show()
    }

    private fun setDateFinish() {
        val date = viewModel.dateFinish.value
        DatePickerDialog(
            requireView().context, onDateFinishSetListener,
            date!![0].toInt(), date[1].toInt(), date[2].toInt()
        ).show()

    }

    private fun setTimeFinish() {
        val time = viewModel.timeFinish.value
        TimePickerDialog(
            requireView().context, onTimeFinishSetListener,
            time!![0].toInt(), time[1].toInt(), true
        ).show()
    }

    private val onDateStartSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            viewModel.setChosenDate(year, month, dayOfMonth)
        }

    private val onTimeStartSetListener =
        TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            viewModel.setChosenTime(hour, minute)
        }

    private val onDateFinishSetListener =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            viewModel.setChosenDateFinish(year, month, dayOfMonth)
        }

    private val onTimeFinishSetListener =
        TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            viewModel.setChosenTimeFinish(hour, minute)
        }
}