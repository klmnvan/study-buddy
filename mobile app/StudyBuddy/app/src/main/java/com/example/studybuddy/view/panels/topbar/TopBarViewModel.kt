package com.example.studybuddy.view.panels.topbar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.domain.room.database.StudyBuddyDatabase
import com.example.studybuddy.view.navigation.NavigationRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val database: StudyBuddyDatabase): ViewModel() {

        fun logOut(controller: NavHostController) {
            try {
                with(database) {
                    teacherDao.deleteAllTeacher()
                    taskDao.deleteAllTask()
                    examDao.deleteAllExams()
                    disciplineDao.deleteAllDisc()
                    noteDao.deleteAllNote()
                    requirementDao.getAllReqs()
                }
                controller.navigate(NavigationRoutes.AUTH)
                {
                    controller.currentDestination?.route?.let {
                        controller.popBackStack(
                            route = it, // Очистить весь стек
                            inclusive = true // Удалить все элементы стека, включая текущий
                        )
                    }
                }
            }
            catch (e: Exception) {
                Log.d("error clearData", e.message.toString())
            }

        }



}