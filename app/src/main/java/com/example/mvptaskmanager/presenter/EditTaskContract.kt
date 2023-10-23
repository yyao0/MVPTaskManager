package com.example.mvptaskmanager.presenter

import com.example.mvptaskmanager.model.Task

interface EditTaskContract {

    interface View {
        fun onTaskUpdated()
        fun displayTask(task: Task)
        fun showError(message: String)
    }

    interface Presenter{
        fun getTask(taskId: Int)
        fun updateTask(task: Task)
        fun onDestroy()
    }
}