package com.example.studybuddy.view.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.studybuddy.data.states.RegisterSt
import com.example.studybuddy.data.states.TasksSt
import com.example.studybuddy.domain.network.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val service: ApiServiceImpl) : ViewModel()  {

    private val _state = MutableStateFlow(TasksSt())
    val state: StateFlow<TasksSt> = _state.asStateFlow()

    var stateValue: TasksSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

}