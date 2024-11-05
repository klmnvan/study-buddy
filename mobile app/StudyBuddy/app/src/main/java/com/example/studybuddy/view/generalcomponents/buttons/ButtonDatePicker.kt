package com.example.studybuddy.view.generalcomponents.buttons

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.studybuddy.R
import com.example.studybuddy.domain.converters.ConvertDate2
import com.example.studybuddy.domain.converters.ConvertLongToTime
import com.example.studybuddy.domain.converters.ConvertLongToTime2
import com.example.studybuddy.view.generalcomponents.spacers.SpacerWidth
import com.example.studybuddy.view.generalcomponents.texts.TextBold
import com.example.studybuddy.view.generalcomponents.texts.TextExtraLight
import com.example.studybuddy.view.ui.theme.StudyBuddyTheme

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ButtonDatePicker(title: String, dateResult: String, onChangeDate: (String) -> Unit) {
    var showDialog by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(StudyBuddyTheme.colors.containerSecondary, RoundedCornerShape(5.dp))
            .padding(vertical = 20.dp, horizontal = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                showDialog = true
            }
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            TextBold(title, 12.sp, StudyBuddyTheme.colors.secondary)
            TextExtraLight(ConvertDate2(dateResult), 12.sp, StudyBuddyTheme.colors.secondary)
            Spacer(modifier = Modifier.weight(1f))
            SpacerWidth(12.dp)
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.icon_clock),
                contentDescription = null,
                tint = StudyBuddyTheme.colors.secondary,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
    if(showDialog) {
        val datePickerSt = rememberDatePickerState()
        val confirmEnabled =
            derivedStateOf {
                datePickerSt.selectedDateMillis != null
            }

        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        var date = "нет"
                        if (datePickerSt.selectedDateMillis != null) {
                            date = ConvertLongToTime2(datePickerSt.selectedDateMillis!!)
                        }
                        onChangeDate(date)
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("ok")
                }
            },
            colors = DatePickerDefaults.colors(
                containerColor = StudyBuddyTheme.colors.background,
                titleContentColor = StudyBuddyTheme.colors.textTitle,
                dividerColor = StudyBuddyTheme.colors.textTitle,
                todayDateBorderColor = StudyBuddyTheme.colors.secondary,
                selectedDayContainerColor = StudyBuddyTheme.colors.secondary,
                selectedYearContentColor = StudyBuddyTheme.colors.secondary,
                selectedDayContentColor = StudyBuddyTheme.colors.secondary,
                yearContentColor = StudyBuddyTheme.colors.secondary,
                navigationContentColor = StudyBuddyTheme.colors.secondary,
                dateTextFieldColors = TextFieldDefaults.colors(
                    unfocusedTextColor = StudyBuddyTheme.colors.secondary,
                    focusedTextColor = StudyBuddyTheme.colors.secondary,
                    unfocusedContainerColor = StudyBuddyTheme.colors.secondary
                ),
                dayInSelectionRangeContentColor = StudyBuddyTheme.colors.secondary,
                subheadContentColor = StudyBuddyTheme.colors.secondary,
                disabledSelectedDayContentColor = StudyBuddyTheme.colors.secondary,
                weekdayContentColor = StudyBuddyTheme.colors.secondary,
                headlineContentColor = StudyBuddyTheme.colors.secondary,
                todayContentColor = StudyBuddyTheme.colors.secondary,
                dayContentColor = StudyBuddyTheme.colors.secondary,
                currentYearContentColor = StudyBuddyTheme.colors.secondary,
                disabledYearContentColor = StudyBuddyTheme.colors.secondary,
                disabledDayContentColor = StudyBuddyTheme.colors.secondary,
                selectedYearContainerColor = StudyBuddyTheme.colors.secondary,
                disabledSelectedYearContentColor = StudyBuddyTheme.colors.secondary,
                dayInSelectionRangeContainerColor = StudyBuddyTheme.colors.secondary,
                disabledSelectedDayContainerColor = StudyBuddyTheme.colors.secondary,
                disabledSelectedYearContainerColor = StudyBuddyTheme.colors.secondary,
            )
        ) {
            DatePicker(state = datePickerSt)
        }
    }
}