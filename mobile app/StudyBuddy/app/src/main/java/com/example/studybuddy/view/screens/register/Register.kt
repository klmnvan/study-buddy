package com.example.studybuddy.view.screens.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.studybuddy.view.generalcomponents.buttons.ButtonFillMaxWidth
import com.example.studybuddy.view.generalcomponents.texts.DoubleText
import com.example.studybuddy.view.generalcomponents.icons.ButtonBack
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
import com.example.studybuddy.view.generalcomponents.texts.TextLight
import com.example.studybuddy.view.generalcomponents.textfields.TextFieldAuthEmail
import com.example.studybuddy.view.generalcomponents.textfields.TextFieldAuthNickname
import com.example.studybuddy.view.generalcomponents.textfields.TextFieldAuthPassword
import com.example.studybuddy.view.generalcomponents.texts.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

/** Экран регистрации */
@Composable
fun Register(controller: NavHostController, viewModel: RegisterViewModel = hiltViewModel()) {

    val state = viewModel.state.collectAsState()

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(vertical = 40.dp, horizontal = 24.dp), verticalArrangement = Arrangement.Center) {
            ButtonBack { viewModel.goBack(controller) }
            Spacer(modifier = Modifier.weight(0.6f))
            Column (modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                TextTitle("Регистрация", 40.sp, StudyBuddyTheme.colors.textTitle)
                SpacerHeight(12.dp)
                TextLight("Создайте свой профиль", 16.sp, StudyBuddyTheme.colors.primary)
            }
            SpacerHeight(60.dp)
            TextFieldAuthNickname(state.value.nickname, { viewModel.stateValue = state.value.copy(nickname = it) }, "nickname")
            SpacerHeight(16.dp)
            TextFieldAuthEmail(state.value.email, { viewModel.stateValue = state.value.copy(email = it) }, "user@mail.ru")
            SpacerHeight(16.dp)
            TextFieldAuthPassword(state.value.password, { viewModel.stateValue = state.value.copy(password = it) }, "********")
            SpacerHeight(16.dp)
            TextFieldAuthPassword(state.value.confirmPassword, { viewModel.stateValue = state.value.copy(confirmPassword = it) }, "********")
            SpacerHeight(40.dp)
            ButtonFillMaxWidth("СОЗДАТЬ ПРОФИЛЬ", StudyBuddyTheme.colors.primary, true) {
                viewModel.signUp(controller)
            }
            Spacer(modifier = Modifier.weight(1f))
            SpacerHeight(20.dp)
            DoubleText("Уже есть профиль? ", "Авторизуйтесь") { viewModel.goLogin(controller) }
        }
    }

}