package com.example.studybuddy.view.screens.schedule

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.states.ScheduleSt
import com.example.studybuddy.domain.network.ApiServiceImpl
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
) : ViewModel()  {

    private val _state = MutableStateFlow(ScheduleSt())
    val state: StateFlow<ScheduleSt> = _state.asStateFlow()

    var stateValue: ScheduleSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun getValues() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getValues()
            if(response.error == "") {
                Log.e("values", response.values.toString())
                stateValue = stateValue.copy(
                    groups = response.values.result.group,
                    cabinets = response.values.result.cabinet,
                    teachers = response.values.result.teacher
                    )
            } else {
                Toast.makeText(context, "Данные не актуальны", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchDisc", response.error)
            }
        }
    }

    fun getSchedule() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getSchedule()
            if(response.error == "") {
                Log.e("values", response.valuesSchedule.toString())
            } else {
                Toast.makeText(context, "Данные не актуальны", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchDisc", response.error)
            }
        }
    }

}