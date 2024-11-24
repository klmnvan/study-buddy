package com.example.studybuddy.view.screens.disciplines

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.dto.CreateDiscDto
import com.example.studybuddy.data.dto.CreateReqDto
import com.example.studybuddy.data.dto.CreateTeacherDto
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.entityes.RequirementEnt
import com.example.studybuddy.data.entityes.TeacherEnt
import com.example.studybuddy.data.states.DisciplinesSt
import com.example.studybuddy.domain.repository.UserRepository
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
                Log.e("предметы в БД", it.toString())
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            database.teacherDao.getAllTeacher().collect {
                stateValue = _state.value.copy(teachers = it)
                Log.e("преподы в БД", it.toString())
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            database.requirementDao.getAllReqs().collect {
                stateValue = _state.value.copy(requirements = it)
                Log.e("требования в БД", it.toString())
            }
        }
    }

    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getTeachers(UserRepository.token)
            if(response.error == "") {
                if(!stateValue.teachers.equals(response.listTeachers) || !stateValue.disciplines.equals(response.listDiscipline)
                    || !stateValue.requirements.equals(response.listRequirements)) {
                    Log.e("предметы из API", response.listDiscipline.toString())
                    Log.e("преподы из API", response.listTeachers.toString())
                    Log.e("требования из API", response.listRequirements.toString())
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
                val response = service.createDisc(
                    UserRepository.token, CreateDiscDto(
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

    fun updateTeacher(el: TeacherEnt, success: (Boolean) -> Unit) {
        if(el.fullName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.updateTeacher(UserRepository.token, el)
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Toast.makeText(context, "Изменено", Toast.LENGTH_SHORT).show()
                    Log.e("tasks", "Я изменил задачу и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error updateTask", response.error)
                }
            }
        } else {
            Toast.makeText(context, "ФИО преподавателя не может быть пустым", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateReq(el: RequirementEnt, success: (Boolean) -> Unit) {
        if(el.content.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.updateReq(UserRepository.token, el)
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Toast.makeText(context, "Изменено", Toast.LENGTH_SHORT).show()
                    Log.e("tasks", "Я изменил требование и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error updateTask", response.error)
                }
            }
        } else {
            Toast.makeText(context, "ФИО преподавателя не может быть пустым", Toast.LENGTH_SHORT).show()
        }
    }

    fun updateDisc(el: DisciplineEnt, success: (Boolean) -> Unit) {
        if(el.title.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.updateDiscipline(UserRepository.token, el)
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Toast.makeText(context, "Изменено", Toast.LENGTH_SHORT).show()
                    Log.e("tasks", "Я изменил предмет и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error updateTask", response.error)
                }
            }
        } else {
            Toast.makeText(context, "ФИО преподавателя не может быть пустым", Toast.LENGTH_SHORT).show()
        }
    }

    fun createTeacher(el: CreateTeacherDto, success: (Boolean) -> Unit) {
        if(el.fullName.isNotEmpty()) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.createTeacher(UserRepository.token, el)
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Toast.makeText(context, "Добавлено", Toast.LENGTH_SHORT).show()
                    Log.e("tasks", "Я создал препода и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error createTeacher", response.error)
                }
            }
        } else {
            Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }

    fun deleteTeacher(el: TeacherEnt) {
        Log.e("tasks", el.toString())
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.deleteTeacher(UserRepository.token, el)
            if(response.error == "") {
                updateValues()
                Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show()
                Log.e("tasks", "Я удалил препода и обновил базу")
            } else {
                Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error deleteTeacher", response.error)
            }
        }
    }

    fun deleteReq(el: RequirementEnt) {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.deleteReq(UserRepository.token, el)
            if(response.error == "") {
                updateValues()
                Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show()
                Log.e("tasks", "Я удалил требование и обновил базу")
            } else {
                Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error deleteTeacher", response.error)
            }
        }
    }

    fun deleteDiscs(el: DisciplineEnt, success: (Boolean) -> Unit) {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.deleteDisc(UserRepository.token, el)
            if(response.error == "") {
                success(true)
                updateValues()
                Toast.makeText(context, "Удалено", Toast.LENGTH_SHORT).show()
                Log.e("tasks", "Я удалил предмет и обновил базу")
            } else {
                success(false)
                Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error deleteTeacher", response.error)
            }
        }
    }

    fun createReq(el: CreateReqDto, success: (Boolean) -> Unit) {
        if(el.content.isNotEmpty() && el.idDiscipline != 0) {
            viewModelScope.launch(Dispatchers.Main) {
                val response = service.createRequirement(UserRepository.token, el)
                if(response.error == "") {
                    success(true)
                    updateValues()
                    Toast.makeText(context, "Добавлено", Toast.LENGTH_SHORT).show()
                    Log.e("tasks", "Я создал требование и обновил базу")
                } else {
                    success(false)
                    Toast.makeText(context, "Данные не изменились", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                    Log.e("error createTeacher", response.error)
                }
            }
        } else {
            Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }



}