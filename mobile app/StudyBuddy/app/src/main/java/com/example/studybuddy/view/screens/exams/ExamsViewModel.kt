package com.example.studybuddy.view.screens.exams

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.studybuddy.data.states.ExamsSt
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
class ExamsViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context,
    private val database: StudyBuddyDatabase,
) : ViewModel()  {

    private val _state = MutableStateFlow(ExamsSt())
    val state: StateFlow<ExamsSt> = _state.asStateFlow()

    var stateValue: ExamsSt
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
            database.examDao.getAllExams().collect {
                stateValue = _state.value.copy(exams = it)
            }
        }
    }


    fun fetch() {
        viewModelScope.launch(Dispatchers.Main) {
            val response = service.getExams(CachedData.tokenUser)
            if(response.error == "") {
                if(!stateValue.exams.equals(response.listExams)) {
                    updateValues()
                }
            } else {
                Toast.makeText(context, "Данные не актуальны", Toast.LENGTH_SHORT).show()
                Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                Log.e("error fetchTasks", response.error)
            }
        }
    }

}