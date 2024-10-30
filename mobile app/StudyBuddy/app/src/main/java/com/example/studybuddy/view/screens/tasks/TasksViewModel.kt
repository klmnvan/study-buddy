package com.example.studybuddy.view.screens.tasks

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.domain.UserRepository
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val database: StudyBuddyDatabase,
) : ViewModel()  {

    private val _state = MutableStateFlow(TasksSt())
    val state: StateFlow<TasksSt> = _state.asStateFlow()

    var stateValue: TasksSt
        get() = _state.value
        set(value) {
            _state.value = value
        }


    fun launch() {
        updateValueFromDB()
        fetchTasks()
    }

    fun updateValueFromDB() {
        viewModelScope.launch(Dispatchers.IO) {
            database.taskDao.getAllTasks().collect {
                stateValue = _state.value.copy(tasks = it)
                Log.e("tasks", "Задачи, сохраненные в room: $it")
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            database.disciplineDao.getAllDiscs().collect {
                stateValue = _state.value.copy(disciplines = it)
                Log.e("disciplines", "Предметы, сохраненные в room: $it")
            }
        }
    }


    fun fetchTasks() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getTasks(UserRepository.token)
            if(response.error == "") {
                if(!stateValue.tasks.equals(response.listTask) || !stateValue.disciplines.equals(response.listDisc)) {
                    updateValueFromDB()
                }
                Log.e("tasks", "Задачи, полученные из API: " + response.listTask.toString())
                Log.e("disc", "Предметы, полученные из API: " + response.listDisc.toString())
            } else {
                Toast.makeText(context, "Данные не актуальны", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchTasks", response.error)
            }
        }
    }

    fun updateTask(el: TaskEnt) {
        Log.e("tasks", el.toString())
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.updateTask(UserRepository.token, el)
            if(response.error == "") {
                updateValueFromDB()
                Log.e("tasks", "Я изменил задачу и обновил базу")
            } else {
                Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error updateTask", response.error)
            }
        }
    }

    fun deleteTask(el: TaskEnt, success: (Boolean) -> Unit) {
        Log.e("tasks", el.toString())
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.deleteTask(UserRepository.token, el)
            if(response.error == "") {
                success(true)
                updateValueFromDB()
                Log.e("tasks", "Я удалил задачу и обновил базу")
            } else {
                success(false)
                Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error deleteTask", response.error)
            }
        }
    }


}