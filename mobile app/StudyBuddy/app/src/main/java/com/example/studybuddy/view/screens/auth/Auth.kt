package com.example.studybuddy.view.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.domain.navigation.NavigationRoutes
import com.example.studybuddy.view.components.ButtonFillMaxWidth
import com.example.studybuddy.view.components.SpacerHeight
import com.example.studybuddy.view.components.SpacerWidth
import com.example.studybuddy.view.components.TextDesc
import com.example.studybuddy.view.components.TextTitle
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Auth(controller: NavHostController) {

    Column(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background)
        .verticalScroll(rememberScrollState())
        .padding(vertical = 40.dp, horizontal = 24.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.mini_logo), contentDescription = null, modifier = Modifier.size(32.dp))
            SpacerWidth(12.dp)
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = StudyBuddyTheme.colors.secondary)) {
                        append("Study  ")
                    }
                    withStyle(SpanStyle(color = StudyBuddyTheme.colors.textTitle)) {
                        append("Buddy")
                    }
                },
                style = StudyBuddyTheme.typography.bold,
                fontSize = 16.sp
            )
        }
        SpacerHeight(40.dp)
        TextTitle("Умное планирование учёбы", 32.sp, StudyBuddyTheme.colors.textTitle)
        SpacerHeight(12.dp)
        TextDesc("Здесь ты сможешь узнавать расписание, готовится к экзаменам, отслеживать дедлайны и добавлять информацию о дисциплинах",
            16.sp, StudyBuddyTheme.colors.primary)
        Spacer(modifier = Modifier.weight(0.4f))
        SpacerHeight(12.dp)
        Column(verticalArrangement = Arrangement.Center) {
            ButtonFillMaxWidth("войти", StudyBuddyTheme.colors.primary, true) {
                controller.navigate(NavigationRoutes.LOGIN) {
                    popUpTo(NavigationRoutes.AUTH) {
                        inclusive = true
                    }
                }
            }
            SpacerHeight(16.dp)
            ButtonFillMaxWidth("СОЗДАТЬ ПРОФИЛЬ", StudyBuddyTheme.colors.secondary, true) {
                controller.navigate(NavigationRoutes.REGIST) {
                    popUpTo(NavigationRoutes.AUTH) {
                        inclusive = true
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }


}
