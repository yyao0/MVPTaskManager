package com.example.mvptaskmanager.presenter

import com.example.mvptaskmanager.model.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TaskListPresenter(
    private var view: TaskListContract.View?,
    private val taskDao: TaskDao
) : TaskListContract.Presenter{

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun loadTasks() {
        coroutineScope.launch {
            try {
                val tasks = withContext(Dispatchers.IO) {
                    taskDao.getAllTasks()
                }
                view?.showTasks(tasks)
            } catch (e: Exception) {
                view?.showError(e.message ?: "Error fetching tasks!")
            }
        }
    }

    override fun onDestroy() {
        view = null
        coroutineScope.cancel()
    }
}