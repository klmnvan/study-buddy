package com.example.studybuddy.view.screens.disciplines

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.entityes.DisciplineEnt
import com.example.studybuddy.data.states.DisciplinesSt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.domain.CachedData
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
                Log.e("disciplines", "Предметы, сохраненные в room: $it")
            }
        }
    }


    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getTasks(CachedData.tokenUser)
            if(response.error == "") {
                if(!stateValue.disciplines.equals(response.listDisc)) {
                    updateValues()
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

}