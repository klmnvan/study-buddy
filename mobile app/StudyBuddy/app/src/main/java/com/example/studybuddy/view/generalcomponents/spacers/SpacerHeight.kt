package com.example.studybuddy.view.generalcomponents.spacers

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun SpacerHeight(height: Dp){
    Spacer(modifier = Modifier.height(height))
}