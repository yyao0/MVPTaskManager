package com.example.mvptaskmanager.presenter

interface AddTaskContract {
    interface View{
        fun onTaskAdded()
        fun showError(message: String)
    }

    interface Presenter{
        fun addTask(title: String, time: String, status: String, content: String)
        fun onDestroy()
    }
}