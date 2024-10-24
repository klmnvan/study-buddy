package com.example.studybuddy.view.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Splash(controller: NavHostController, viewModel: SplashViewModel = hiltViewModel()) {

    viewModel.context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.launch(controller)
    }

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background), contentAlignment = Alignment.Center) {
        Image(imageVector = ImageVector.vectorResource(R.drawable.logo), contentDescription = null,
            modifier = Modifier.fillMaxWidth())
    }


}