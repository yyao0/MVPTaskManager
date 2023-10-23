package com.example.mvptaskmanager.presenter

import com.example.mvptaskmanager.model.Task
import com.example.mvptaskmanager.model.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTaskPresenter(
    private val view: AddTaskContract.View,
    private val taskDao: TaskDao
) : AddTaskContract.Presenter{

    private val coroutineScope = CoroutineScope(Dispatchers.Main + Job())

    override fun addTask(title: String, time: String, status: String, content: String) {
        if (title.isEmpty() || time.isEmpty() || status.isEmpty() || content.isEmpty()) {
            view.showError("All fields are required!")
            return
        }

        val newTask = Task(id = 0, title = title, time = time, status = status, content = content)

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                val taskId = taskDao.insertTask(newTask)
                withContext(Dispatchers.Main) {
                    if (taskId > 0) {
                        view.onTaskAdded()
                    } else {
                        view.showError("Failed to add task!")
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        coroutineScope.cancel()
    }
}