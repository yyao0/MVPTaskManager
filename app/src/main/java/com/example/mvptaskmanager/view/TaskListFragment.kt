package com.example.mvptaskmanager.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.mvptaskmanager.R
import com.example.mvptaskmanager.databinding.FragmentTaskListBinding
import com.example.mvptaskmanager.model.AppDatabase
import com.example.mvptaskmanager.model.Task
import com.example.mvptaskmanager.model.TaskDao
import com.example.mvptaskmanager.presenter.TaskListContract
import com.example.mvptaskmanager.presenter.TaskListPresenter


class TaskListFragment : Fragment(), TaskListContract.View {

    private lateinit var binding: FragmentTaskListBinding
    private lateinit var presenter: TaskListContract.Presenter
    private val taskDao: TaskDao by lazy { AppDatabase.getDatabase(requireContext()).taskDao() }
    private lateinit var taskAdapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        binding.btnAdd.setOnClickListener {
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddTaskFragment())
                .addToBackStack(null)
                .commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        taskAdapter = TaskAdapter(emptyList()) {selectedTask ->
            val fragment = EditTaskFragment().apply {
                arguments = Bundle().apply {
                    putInt("taskId", selectedTask.id)
                }
            }
            fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.fragment_container, fragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        presenter = TaskListPresenter(this, taskDao)
        presenter.loadTasks()
        binding.rvTasks.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTasks.adapter = taskAdapter
    }

    override fun showTasks(tasks: List<Task>) {
        taskAdapter.updateTasks(tasks)
    }

    override fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


}