package com.example.studybuddy.view.screens.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.example.studybuddy.domain.repository.UserRepository
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import com.example.studybuddy.view.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val database: StudyBuddyDatabase
) : ViewModel() {

    fun launch(navController: NavHostController) {
        viewModelScope.launch {
            delay(2000L)
            if(UserRepository.tokenIsValid()){
                navController.navigate(NavigationRoutes.TASKS) {
                    popUpTo(NavigationRoutes.SPLASH) {
                        inclusive = true
                    }
                }
            } else {
                logOut()
                navController.navigate(NavigationRoutes.AUTH) {
                    popUpTo(NavigationRoutes.SPLASH) {
                        inclusive = true
                    }
                }
            }
        }
    }

    private fun logOut() {
        try {
            with(database) {
                teacherDao.deleteAllTeacher()
                taskDao.deleteAllTask()
                examDao.deleteAllExams()
                disciplineDao.deleteAllDisc()
                noteDao.deleteAllNote()
                requirementDao.getAllReqs()
            }
        }
        catch (e: Exception) {
            Log.d("error clearData", e.message.toString())
        }

    }

}
