package com.vk59.diary_simbirsoft.ui.main.recycler_view

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.vk59.diary_simbirsoft.R
import com.vk59.diary_simbirsoft.databinding.TaskDescriptionItemBinding
import com.vk59.diary_simbirsoft.databinding.TaskItemBinding
import com.vk59.diary_simbirsoft.model.Task
import java.util.*
import kotlin.time.days

class HourRecyclerAdapter(private var context: Context) :
    RecyclerView.Adapter<HourRecyclerAdapter.HourViewHolder>() {
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

    override fun onBindViewHolder(holder: HourViewHolder, position: Int) {
        if (tasks.size == 0) {
            bindEmptyTask(holder)
        }
        tasks[position].let {
            bindTask(it, holder)
            val task = it
            holder.binding.root.setOnClickListener {
                val dialog = Dialog(context)
                dialog.setTitle(task.name)
                dialog.setContentView(R.layout.task_fragment)
                val taskNameText = dialog.findViewById<TextView>(R.id.taskNameText)
                val taskDescription = dialog.findViewById<TextView>(R.id.taskDescriptionText)
                val dateStartText = dialog.findViewById<TextView>(R.id.dateStartText)
                val dateFinishText = dialog.findViewById<TextView>(R.id.dateFinishText)
                taskNameText.text = task.name
                taskDescription.text = task.description
                dateStartText.text = "Date start: ${task.dateStart}"
                dateFinishText.text = "Date finish: ${task.dateStart}"

                dialog.findViewById<Button>(R.id.backButton).setOnClickListener {
                    dialog.onBackPressed()
                }
                dialog.show()
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
