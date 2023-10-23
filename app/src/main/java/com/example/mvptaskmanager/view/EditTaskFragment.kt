package com.example.mvptaskmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mvptaskmanager.R
import com.example.mvptaskmanager.databinding.FragmentEditTaskBinding
import com.example.mvptaskmanager.model.AppDatabase
import com.example.mvptaskmanager.model.Task
import com.example.mvptaskmanager.model.TaskDao
import com.example.mvptaskmanager.presenter.EditTaskContract
import com.example.mvptaskmanager.presenter.EditTaskPresenter


class EditTaskFragment : Fragment(), EditTaskContract.View {

    private lateinit var binding: FragmentEditTaskBinding
    private lateinit var presenter: EditTaskContract.Presenter
    private val taskDao: TaskDao by lazy { AppDatabase.getDatabase(requireContext()).taskDao() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater, container, false)
        presenter = EditTaskPresenter(this, taskDao)
        val taskId = arguments?.getInt("taskId") ?: return binding.root

        binding.btnUpdate.setOnClickListener {
            val updatedTask = Task(
                id = taskId,
                title = binding.etTitle.text.toString(),
                time = binding.etTime.text.toString(),
                status = binding.etStatus.text.toString(),
                content = binding.etContent.text.toString()
            )
            presenter.updateTask(updatedTask)
        }
        presenter.getTask(taskId)
        return binding.root
    }


    override fun onTaskUpdated() {
        Toast.makeText(context, "Task Updated Successfully!", Toast.LENGTH_SHORT).show()
        fragmentManager?.popBackStack()
    }

    override fun displayTask(task: Task) {
        binding.etTitle.setText(task.title)
        binding.etTime.setText(task.time)
        binding.etStatus.setText(task.status)
        binding.etContent.setText(task.content)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}