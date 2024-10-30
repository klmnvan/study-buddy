package com.example.studybuddy.view.screens.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.data.states.LoginSt
import com.example.studybuddy.domain.UserRepository
import com.example.studybuddy.domain.navigation.NavigationRoutes
import com.example.studybuddy.domain.verification.AuthVerification.isEmailValid
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = MutableStateFlow(LoginSt())
    val state: StateFlow<LoginSt> = _state.asStateFlow()

    var stateValue: LoginSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun signIn(controller: NavHostController) {
        if(stateValue.email != "" && stateValue.password != "") {
            if(stateValue.email.isEmailValid()) {
                viewModelScope.launch {
                    val response = service.signIn(stateValue.email, stateValue.password)
                    if(response.error == "") {
                        //Toast.makeText(context, "${response.user?.token}", Toast.LENGTH_SHORT).show()
                        controller.navigate(NavigationRoutes.TASKS) {
                            popUpTo(NavigationRoutes.LOGIN) {
                                inclusive = true
                            }
                        }
                        UserRepository.token = response.user?.token ?: UserRepository.token
                    } else {
                        Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                        Log.e("error signIn",response.error)
                    }
                }
            }
            else Toast.makeText(context, "Неверный формат почты", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
    }

    fun goBack(controller: NavHostController) {
        controller.navigate(NavigationRoutes.AUTH) {
            popUpTo(NavigationRoutes.LOGIN) {
                inclusive = true
            }
        }
    }

    fun goRegist(controller: NavHostController) {
        controller.navigate(NavigationRoutes.REGIST) {
            popUpTo(NavigationRoutes.LOGIN) {
                inclusive = true
            }
        }
    }


}