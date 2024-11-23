package com.example.studybuddy.view.panels.topbar

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.domain.UserRepository
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
                    UserRepository.token = "eyJhbGciOiJIUzUxMiIsInR5cCI6IkpXVCJ9.eyJuYW1lIjoidXNlckBleGFtcGxlLmNvbSIsImdpdmVuX25hbWUiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVzZXIiLCJ1bmlxdWVfbmFtZSI6InVzZXJAZXhhbXBsZS5jb20iLCJuYW1laWQiOiI0ZTFlYjc4Mi1iOWIzLTQ3ZWItYjg0Yy05MDhhZTQzZWY2YTciLCJlbWFpbCI6InVzZXJAZXhhbXBsZS5jb20iLCJuYmYiOjE3MzEwMTMyMzIsImV4cCI6MTczMTYxODAzMiwiaWF0IjoxNzMxMDEzMjMyLCJpc3MiOiJJc3N1ZXIiLCJhdWQiOiJBdWRpZW5jZSJ9.QdHwsNS_HRFh1sTaXEVsJ8ZZd3bds1_HYgQbHv3EekI_uolDeMgYj37V-yHTilVan9zzfNkhdsoRmZHdJV3giA"
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