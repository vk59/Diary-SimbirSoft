package com.vk59.diary_simbirsoft.ui.main.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.databinding.TaskItemBinding
import com.vk59.diary_simbirsoft.model.Task

class TodayRecyclerAdapter(private var context: Context) :
        RecyclerView.Adapter<TodayRecyclerAdapter.TodayViewHolder>() {
    val LOG = "TODAY RECYCLER ADAPTER"

    private var tasks: List<ArrayList<Task>>? = null

    fun setTasks(tasks: List<ArrayList<Task>>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayViewHolder {
        val holder = TodayViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.task_item,
                parent,
                false
            )
        )
        return holder
    }

    // TODO: 03.07.2021 Implement observing changing in currentTasks and viewing on screen
    override fun onBindViewHolder(holder: TodayViewHolder, position: Int) {
        holder.binding.time.text = "$position - ${(position + 1) % 24}"
        tasks?.let {
            val hourAdapter = HourRecyclerAdapter()
            holder.binding.tasksThisHourRecycler.layoutManager = LinearLayoutManager(context)
            holder.binding.tasksThisHourRecycler.adapter = hourAdapter
            hourAdapter.setTasks(it[position])
        }
    }

    override fun getItemCount(): Int {
        return tasks?.size ?: 0
    }


    class TodayViewHolder(
            val binding: TaskItemBinding
    ) :
            RecyclerView.ViewHolder(binding.root)
}