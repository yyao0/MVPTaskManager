package com.example.mvptaskmanager.presenter

import com.example.mvptaskmanager.model.Task
import com.example.mvptaskmanager.model.TaskDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditTaskPresenter(
    private var view: EditTaskContract.View?,
    private val taskDao: TaskDao
) : EditTaskContract.Presenter{
    override fun getTask(taskId: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val task = taskDao.getTaskById(taskId)
            withContext(Dispatchers.Main) {
                task?.let { view?.displayTask(it) }
            }
        }
    }

    override fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.IO).launch{
            taskDao.updateTask(task)
            withContext(Dispatchers.Main) {
                view?.onTaskUpdated()
            }
        }
    }

    override fun onDestroy() {
        view = null
    }
}