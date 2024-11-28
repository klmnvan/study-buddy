package com.example.studybuddy.view

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application() {

/*    override fun onCreate() {
        super.onCreate()
        //канал для отправки уведомлений
        val notificationChannel = NotificationChannel("id", "Deadline channel", NotificationManager.IMPORTANCE_HIGH)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }*/

}