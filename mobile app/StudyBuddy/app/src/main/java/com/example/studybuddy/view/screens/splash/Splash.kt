package com.example.studybuddy.view.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

/** СплэшСкрин */
@Composable
fun Splash(controller: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {

    LaunchedEffect(Unit) {
        viewModel.launch(controller)
    }

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background), contentAlignment = Alignment.Center) {
        Image(imageVector = ImageVector.vectorResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier.fillMaxWidth())
    }


}
