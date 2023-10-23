package com.example.mvptaskmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.mvptaskmanager.R
import com.example.mvptaskmanager.databinding.FragmentAddTaskBinding
import com.example.mvptaskmanager.model.AppDatabase
import com.example.mvptaskmanager.model.TaskDao
import com.example.mvptaskmanager.presenter.AddTaskContract
import com.example.mvptaskmanager.presenter.AddTaskPresenter


class AddTaskFragment : Fragment(), AddTaskContract.View {

    private lateinit var binding: FragmentAddTaskBinding
    private lateinit var presenter: AddTaskContract.Presenter
    private val taskDao: TaskDao by lazy { AppDatabase.getDatabase(requireContext()).taskDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        binding.btnSave.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val time = binding.etTime.text.toString()
            val status = binding.etStatus.text.toString()
            val content = binding.etContent.text.toString()

            presenter.addTask(title, time, status, content)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AddTaskPresenter(this, taskDao)
    }

    override fun onTaskAdded() {
        Toast.makeText(context, "Task Added Successfully!", Toast.LENGTH_SHORT).show()
        val fragmentManager = parentFragmentManager
        fragmentManager.beginTransaction()
            .replace(R.id.fragment_container, TaskListFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


}