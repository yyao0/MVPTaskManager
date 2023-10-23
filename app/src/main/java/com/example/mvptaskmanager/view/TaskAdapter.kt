package com.example.mvptaskmanager.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mvptaskmanager.databinding.ItemTaskBinding
import com.example.mvptaskmanager.model.Task

class TaskAdapter(
    private var tasks: List<Task>,
    private val onLongPress: (Task) -> Unit
    ): RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    fun updateTasks(newTasks: List<Task>) {
        tasks = newTasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task, onLongPress)
    }

    override fun getItemCount(): Int = tasks.size

    inner class ViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task, onLongPress: (Task) -> Unit) {
            binding.apply {
                tvTitle.text = task.title
                tvTime.text = task.time
                tvStatus.text = task.status
                tvContent.text = task.content
                root.setOnLongClickListener {
                    onLongPress(task)
                    true
                }
            }

        }
    }
}