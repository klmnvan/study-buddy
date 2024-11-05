package com.example.studybuddy.view.screens.register

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.studybuddy.data.states.RegisterSt
import com.example.studybuddy.view.navigation.NavigationRoutes
import com.example.studybuddy.domain.network.ApiServiceImpl
import com.example.studybuddy.domain.verification.AuthVerification.isEmailValid
import com.example.studybuddy.domain.verification.AuthVerification.isNicknameValid
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val service: ApiServiceImpl,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = MutableStateFlow(RegisterSt())
    val state: StateFlow<RegisterSt> = _state.asStateFlow()

    var stateValue: RegisterSt
        get() = _state.value
        set(value) {
            _state.value = value
        }

    fun goBack(controller: NavHostController) {
        controller.navigate(NavigationRoutes.AUTH) {
            popUpTo(NavigationRoutes.REGIST) {
                inclusive = true
            }
        }
    }

    fun signUp() {
        if(stateValue.email != "" && stateValue.password != "" && stateValue.confirmPassword != "" && stateValue.nickname != "") {
            if(stateValue.email.isEmailValid()) {
                if(stateValue.password == stateValue.confirmPassword) {
                    if(stateValue.nickname.isNicknameValid()){
                        if(stateValue.password.length >= 8){

                            viewModelScope.launch {
                                val response = service.signUp(stateValue.email, stateValue.password, stateValue.confirmPassword, stateValue.nickname)
                                if(response.error == "") {
                                    Toast.makeText(context, "Пользователь создан", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context, response.error, Toast.LENGTH_SHORT).show()
                                    Log.e("error signUp",response.error)
                                }
                            }

                        }
                        else Toast.makeText(context, "Длина пароля должна быть от 8 до 64 символов", Toast.LENGTH_SHORT).show()
                    }
                    else Toast.makeText(context, "В никнейме недопустимые символы", Toast.LENGTH_SHORT).show()
                }
                else Toast.makeText(context, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(context, "Неверный формат почты", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(context, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
    }


    fun goLogin(controller: NavHostController) {
        controller.navigate(NavigationRoutes.LOGIN) {
            popUpTo(NavigationRoutes.REGIST) {
                inclusive = true
            }
        }
    }

}