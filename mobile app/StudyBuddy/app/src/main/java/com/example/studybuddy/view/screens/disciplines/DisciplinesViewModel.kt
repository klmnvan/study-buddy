package com.example.studybuddy.view.screens.disciplines

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.dto.CreateDiscDto
import com.example.studybuddy.data.dto.CreateTaskDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.TaskEnt
import com.example.studybuddy.data.states.DisciplinesSt
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
class DisciplinesViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val database: StudyBuddyDatabase,
) : ViewModel()  {

    private val _state = MutableStateFlow(DisciplinesSt())
    val state: StateFlow<DisciplinesSt> = _state.asStateFlow()

    var stateValue: DisciplinesSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun launch() {
        updateValues()
        fetch()
    }

    fun updateValues() {
        viewModelScope.launch(Dispatchers.IO) {
            database.disciplineDao.getAllDiscs().collect {
                stateValue = _state.value.copy(disciplines = it)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            database.teacherDao.getAllTeacher().collect {
                stateValue = _state.value.copy(teachers = it)
            }
        }
    }


    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getTeachers(UserRepository.token)
            if(response.error == "") {
                if(!stateValue.teachers.equals(response.listTeachers) || !stateValue.disciplines.equals(response.listDiscipline)) {
                    updateValues()
                }
            } else {
                Toast.makeText(context, "Данные не актуальны", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchDisc", response.error)
            }
        }
    }

    fun createDisc(el: DisciplineEnt, success: (Boolean) -> Unit) {
        if(el.title.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.createDisc(UserRepository.token, CreateDiscDto(
                    title = el.title,
                    idTeacher = el.idTeacher)
                )
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Log.e("tasks", "Я создал преподавателя и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error deleteTask", response.error)
                }
            }
        } else {
            Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }

}