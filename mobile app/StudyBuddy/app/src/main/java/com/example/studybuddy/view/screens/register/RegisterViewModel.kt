package com.example.studybuddy.view.screens.register

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.data.states.RegisterSt
import com.example.studybuddy.domain.navigation.NavigationRoutes
import com.example.studybuddy.domain.network.ApiServiceImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val service: ApiServiceImpl
): ViewModel() {

    private val _state = MutableStateFlow(RegisterSt())
    val state: StateFlow<RegisterSt> = _state.asStateFlow()

    var stateValue: RegisterSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    @SuppressLint("StaticFieldLeak")
    lateinit var context: Context

    fun goBack(controller: NavHostController) {
        controller.navigate(NavigationRoutes.AUTH) {
            popUpTo(NavigationRoutes.REGIST) {
                inclusive = true
            }
        }
    }

    fun goLogin(controller: NavHostController) {
        controller.navigate(NavigationRoutes.LOGIN) {
            popUpTo(NavigationRoutes.REGIST) {
                inclusive = true
            }
        }
    }

}