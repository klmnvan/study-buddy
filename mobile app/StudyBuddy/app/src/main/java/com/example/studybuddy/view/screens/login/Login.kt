package com.example.studybuddy.view.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.view.components.ButtonFillMaxWidth
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.TextDesc
import com.example.studybuddy.view.components.TextFieldAuth
import com.example.studybuddy.view.components.TextFieldAuthPassword
import com.example.studybuddy.view.components.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Login(controller: NavHostController, viewModel: LoginViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()
    viewModel.context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)
        .padding(vertical = 40.dp, horizontal = 24.dp)
        .verticalScroll(rememberScrollState()), contentAlignment = Alignment.Center) {
        Column {
            Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextTitle("Авторизация", 40.sp, StudyBuddyTheme.colors.textTitle)
                SpacerHeight(12.dp)
                TextDesc("Войдите в свой профиль", 16.sp, StudyBuddyTheme.colors.primary)
            }
            SpacerHeight(60.dp)
            TextFieldAuth(state.value.email, { viewModel.stateValue = state.value.copy(email = it) }, "user@mail.ru")
            SpacerHeight(16.dp)
            TextFieldAuthPassword(state.value.password, { viewModel.stateValue = state.value.copy(password = it) }, "********")
            SpacerHeight(40.dp)
            ButtonFillMaxWidth("войти", StudyBuddyTheme.colors.primary, true) {
                viewModel.signIn()
            }

        }
    }

}