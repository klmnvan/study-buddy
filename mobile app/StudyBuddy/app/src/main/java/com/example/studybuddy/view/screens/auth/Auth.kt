package com.example.studybuddy.view.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.studybuddy.R
import com.example.studybuddy.view.components.SpacerWidth
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@Composable
fun Auth(controller: NavHostController) {

    Box(modifier = Modifier.fillMaxSize().background(StudyBuddyTheme.colors.background).padding(vertical = 40.dp, horizontal = 24.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painter = painterResource(R.drawable.mini_logo)  , contentDescription = null)
            SpacerWidth(12.dp)
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = StudyBuddyTheme.colors.background)) {
                        append("Study  ")
                    }
                    withStyle(SpanStyle(color = StudyBuddyTheme.colors.background)) {
                        append("Buddy")
                    }
                },
                style = StudyBuddyTheme.typography.bold16,
                modifier = Modifier.clickable {
                //controller!!.navigate(RoutesNavigation.SIGNUP)
                    },
                fontSize = 16.sp
            )
        }

    }


}
