package com.example.mvptaskmanager.presenter

import com.example.mvptaskmanager.model.Task

interface TaskListContract {

    interface View {
        fun showTasks(tasks: List<Task>)
        fun showError(message: String)
    }

    interface Presenter {
        fun loadTasks()
        fun onDestroy()
    }
}