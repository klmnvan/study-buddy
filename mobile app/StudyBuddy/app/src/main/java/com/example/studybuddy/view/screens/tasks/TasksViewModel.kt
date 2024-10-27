package com.example.studybuddy.view.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.room.dao.TaskDao
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val tasksDao: TaskDao,
) : ViewModel()  {

    private val _state = MutableStateFlow(TasksSt())
    val state: StateFlow<TasksSt> = _state.asStateFlow()

    var stateValue: TasksSt
        get() = _state.value
        set(value) {
            _state.value = value
        }


    init {
        getTasksFromDb()
        fetchTasks()
    }

    fun getTasksFromDb() {
        viewModelScope.launch(Dispatchers.IO) {
            tasksDao.getAllTasks().collect {
                stateValue = _state.value.copy(tasks = it)
                Log.e("tasks", "Задачи, сохраненные в room: $it")
            }
        }
    }


    fun fetchTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getTasks("eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3Mjk5ODI1NTQsImV4cCI6MTczMDU4NzM1NCwiaWF0IjoxNzI5OTgyNTU0LCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.y_xPEutXTnLgylLtGDgB5nLPmuSXB9bK3b4dOJj3vnb9SrD5cAWPE_ci1tnJ1SZqRlSu_fysHGKTHllpvvvZpg")
            if(response.error == "") {
                if(!stateValue.tasks.equals(response.listTask)) {
                    getTasksFromDb()
                }
                Log.e("tasks", "Задачи, полученные из API: " + response.listTask.toString())
            } else {
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchTasks", response.error)
            }
        }
    }

}