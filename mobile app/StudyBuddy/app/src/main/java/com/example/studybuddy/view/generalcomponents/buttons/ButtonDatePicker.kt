package com.example.studybuddy.view.generalcomponents.buttons

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.studybuddy.R
import com.example.studybuddy.domain.converters.ConvertDate2
import com.example.studybuddy.domain.converters.ConvertLongToTime
import com.example.studybuddy.domain.converters.ConvertLongToTime2
import com.example.studybuddy.view.generalcomponents.spacers.SpacerHeight
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
                        enabled = confirmEnabled.value,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = StudyBuddyTheme.colors.secondary,
                            disabledContentColor = StudyBuddyTheme.colors.secondary.copy(alpha = 0.6f)
                        )
                    ) {
                        Text("ok")
                    }
                },
                colors = DatePickerDefaults.colors(
                    containerColor = StudyBuddyTheme.colors.background,
                    selectedDayContentColor = StudyBuddyTheme.colors.primary
                )

            ) {
                DatePicker(state = datePickerSt,
                    colors = DatePickerDefaults.colors(
                        containerColor = StudyBuddyTheme.colors.background,
                        titleContentColor = StudyBuddyTheme.colors.textTitle,
                        dividerColor = StudyBuddyTheme.colors.textDesc,
                        todayDateBorderColor = StudyBuddyTheme.colors.primary,
                        selectedDayContainerColor = StudyBuddyTheme.colors.containerSecondary,
                        selectedYearContentColor = StudyBuddyTheme.colors.secondary,
                        selectedDayContentColor = StudyBuddyTheme.colors.secondary,
                        yearContentColor = StudyBuddyTheme.colors.textTitle,
                        navigationContentColor = StudyBuddyTheme.colors.textTitle,
                        dateTextFieldColors = TextFieldDefaults.colors(
                            unfocusedTextColor = StudyBuddyTheme.colors.textTitle,
                            focusedTextColor = StudyBuddyTheme.colors.textTitle,
                            unfocusedContainerColor = StudyBuddyTheme.colors.background,
                            focusedContainerColor = StudyBuddyTheme.colors.background,
                            disabledTextColor = StudyBuddyTheme.colors.textTitle,
                            disabledLabelColor = StudyBuddyTheme.colors.primary,
                            unfocusedLabelColor = StudyBuddyTheme.colors.primary,
                            focusedLabelColor = StudyBuddyTheme.colors.primary,
                            disabledContainerColor = StudyBuddyTheme.colors.background,
                            focusedIndicatorColor = StudyBuddyTheme.colors.primary,
                            unfocusedIndicatorColor = StudyBuddyTheme.colors.primary,
                            cursorColor = StudyBuddyTheme.colors.primary,
                            focusedPlaceholderColor = StudyBuddyTheme.colors.primary,
                            focusedSupportingTextColor = StudyBuddyTheme.colors.primary,
                            errorContainerColor = StudyBuddyTheme.colors.background,
                            errorLeadingIconColor = StudyBuddyTheme.colors.primary,
                            disabledSupportingTextColor = StudyBuddyTheme.colors.primary,
                            errorTextColor = StudyBuddyTheme.colors.secondary,
                            errorIndicatorColor = StudyBuddyTheme.colors.secondary,
                            errorLabelColor = StudyBuddyTheme.colors.secondary,
                            errorCursorColor = StudyBuddyTheme.colors.secondary,
                            errorSupportingTextColor = StudyBuddyTheme.colors.secondary
                        ),
                        dayInSelectionRangeContentColor = StudyBuddyTheme.colors.secondary,
                        subheadContentColor = StudyBuddyTheme.colors.secondary,
                        disabledSelectedDayContentColor = StudyBuddyTheme.colors.secondary,
                        weekdayContentColor = StudyBuddyTheme.colors.primary,
                        headlineContentColor = StudyBuddyTheme.colors.textTitle,
                        todayContentColor = StudyBuddyTheme.colors.primary,
                        dayContentColor = StudyBuddyTheme.colors.textTitle,
                        currentYearContentColor = StudyBuddyTheme.colors.primary,
                        disabledYearContentColor = StudyBuddyTheme.colors.secondary,
                        disabledDayContentColor = StudyBuddyTheme.colors.secondary,
                        selectedYearContainerColor = StudyBuddyTheme.colors.containerSecondary,
                        disabledSelectedYearContentColor = StudyBuddyTheme.colors.secondary,
                        dayInSelectionRangeContainerColor = StudyBuddyTheme.colors.secondary,
                        disabledSelectedDayContainerColor = StudyBuddyTheme.colors.secondary,
                        disabledSelectedYearContainerColor = StudyBuddyTheme.colors.secondary
                    )
                )
            }

    }
}
