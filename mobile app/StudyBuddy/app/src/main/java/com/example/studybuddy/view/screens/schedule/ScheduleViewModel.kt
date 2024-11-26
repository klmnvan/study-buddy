package com.example.studybuddy.view.screens.schedule

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.states.ScheduleSt
import com.example.studybuddy.domain.converters.DateToTimestamp
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.repository.UserRepository
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
class ScheduleViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val database: StudyBuddyDatabase,
) : ViewModel()  {

    private val _state = MutableStateFlow(ScheduleSt())
    val state: StateFlow<ScheduleSt> = _state.asStateFlow()

    var stateValue: ScheduleSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun updateValues() {
        viewModelScope.launch(Dispatchers.IO) {
            database.groupDao.getAllGroups().collect {
                stateValue = _state.value.copy(group = it)
                Log.d("группы из локальной базы", it.toString())
            }
        }
    }

    fun getValues() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getValues()
            if(response.error == "") {
                if(!stateValue.group.equals(response.values.result.group)) {
                    updateValues()
                    Log.d("группы из API", response.values.result.group.toString())
                }
            } else {
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchDisc", response.error)
            }
        }

    }

    fun getSchedule(date: String) {
        if(stateValue.selGroup.id != 0) {
            viewModelScope.launch(Dispatchers.Main) {
                val dateTimestamp = DateToTimestamp(date)
                if(dateTimestamp != null) {
                    val response = service.getSchedule(stateValue.selGroup.id, dateTimestamp)
                    if(response.error == "") {
                        if(stateValue.selGroup.name != null) {
                            UserRepository.lastGroupId = stateValue.selGroup.id
                            UserRepository.lastGroupName = stateValue.selGroup.name!!
                        }
                        stateValue = stateValue.copy(valuesSchedule = response.valuesSchedule)
                        Log.e("values", response.valuesSchedule.toString())
                    } else {
                        Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                        Log.e("error getSchedule", response.error)
                    }
                }
            }
        } else {
            Toast.makeText(context,"Не все поля заполнены", Toast.LENGTH_SHORT).show()
        }
    }

}