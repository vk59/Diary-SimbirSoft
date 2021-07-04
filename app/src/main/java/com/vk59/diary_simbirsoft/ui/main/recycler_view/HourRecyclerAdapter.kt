package com.vk59.diary_simbirsoft.ui.main.recycler_view

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.databinding.TaskDescriptionItemBinding
import com.vk59.diary_simbirsoft.databinding.TaskItemBinding
import com.vk59.diary_simbirsoft.model.Task
import java.util.*

class HourRecyclerAdapter : RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder>() {
    private val LOG = "HOUR RECYCLER ADAPTER"

    private var tasks = arrayListOf<Task>()

    fun setTasks(tasks: ArrayList<Task>) {
        this.tasks.clear()
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourViewHolder =
        HourViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.task_description_item,
                parent,
                false
            )
        )

    // TODO: 03.07.2021 Implement observing changing in currentTasks and viewing on screen
    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        if (tasks.size == 0) {
            bindEmptyTask(holder)
        }
        tasks.let {
            it.forEach {
                bindTask(it, holder)
            }
        }
    }

    private fun bindEmptyTask(holder: HourViewHolder) {
        holder.binding.titleItem.text = ""
        holder.binding.timeItem.text = ""
    }

    private fun bindTask(task: Task, holder: HourViewHolder) {
        holder.binding.task = task
        holder.binding.timeItem.text = periodOfTime(task.dateStart, task.dateFinish)
        Log.d(LOG, holder.binding.timeItem.text.toString())
    }

    private fun periodOfTime(dateStart: Date, dateFinish: Date): String {
        val minutesStart = (if (dateStart.minutes < 10) "0" else "") + dateStart.minutes.toString()
        val minutesFinish =
            (if (dateFinish.minutes < 10) "0" else "") + dateFinish.minutes.toString()
        return "${dateStart.hours}:${minutesStart} - ${dateFinish.hours}:${minutesFinish}"
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    class HourViewHolder(
        val binding: TaskDescriptionItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root)
}
