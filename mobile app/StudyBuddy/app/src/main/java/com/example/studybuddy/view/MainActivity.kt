package com.example.studybuddy.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import androidx.navigation.compose.rememberNavController
import com.example.studybuddy.R
import com.example.studybuddy.view.navigation.Navigation
import com.example.studybuddy.domain.repository.UserRepository
import com.example.studybuddy.view.generalcomponents.pullrefresh.CustomPullToRefreshContainer
import com.example.studybuddy.view.panels.bottombar.BottomBar
import com.example.studybuddy.view.panels.topbar.TopBar
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "InlinedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserRepository.init(LocalContext.current)
            val controller = rememberNavController()
            val barsIsVisible = remember { mutableStateOf(false) }
            val pullToRefreshState = rememberPullToRefreshState()
            val currentThemeMode = remember { mutableStateOf(UserRepository.themes.first { it.title == UserRepository.theme }) }
            StudyBuddyTheme(themeMode = currentThemeMode.value) {
                Scaffold(
                    topBar = {
                        if (barsIsVisible.value) {
                            TopBar(controller, currentThemeMode)
                        }
                    },
                    bottomBar = {
                        if (barsIsVisible.value) {
                            BottomBar(controller)
                        }
                    },
                    floatingActionButton = {
                        CustomPullToRefreshContainer(pullToRefreshState)
                    }
                ) {
                    Navigation(controller, barsIsVisible, pullToRefreshState)
                }
            }
            /*val postNotificationPermission =
                rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)

            val waterNotificationService = WaterNotificationService(this)

            LaunchedEffect(key1 = true) {
                if (!postNotificationPermission.status.isGranted) {
                    postNotificationPermission.launchPermissionRequest()
                }
            }

            Column {

                Button(
                    onClick = {
                        waterNotificationService.showExpandableNotification()
                    }
                ) {
                    Text(text = "Show expandable with image notification")
                }
            }*/
        }
    }
}

class WaterNotificationService(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)

    fun showExpandableNotification() {
        val image = context.bitmapFromResource(R.drawable.mini_logo)

        val notification = NotificationCompat.Builder(context, "water_reminder")
            .setContentTitle("Water Reminder")
            .setContentText("Time to drink some water!")
            .setSmallIcon(R.drawable.mini_logo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLargeIcon(image)
            .setStyle(
                NotificationCompat
                    .BigPictureStyle()
                    .bigPicture(image)
                    .bigLargeIcon(null as Bitmap?)
            )
            .setAutoCancel(true)
            .build()

        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }

    private fun Context.bitmapFromResource(
        @DrawableRes resId: Int
    ) = BitmapFactory.decodeResource(
        resources,
        resId
    )
}
