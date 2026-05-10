package com.classschedule.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.classschedule.data.ScheduleRepository
import com.classschedule.model.Period
import com.classschedule.model.SchoolDay
import com.classschedule.model.Subject
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun ScheduleGrid(modifier: Modifier = Modifier) {
    val horizontalScrollState = rememberScrollState()
    var selectedInfo by remember { mutableStateOf<Pair<Subject, Period>?>(null) }
    val todaySchoolDay = when (LocalDate.now().dayOfWeek) {
        DayOfWeek.MONDAY -> SchoolDay.MONDAY
        DayOfWeek.TUESDAY -> SchoolDay.TUESDAY
        DayOfWeek.WEDNESDAY -> SchoolDay.WEDNESDAY
        DayOfWeek.THURSDAY -> SchoolDay.THURSDAY
        DayOfWeek.FRIDAY -> SchoolDay.FRIDAY
        else -> null
    }

    Column(modifier = modifier.horizontalScroll(horizontalScrollState)) {
        // Day-of-week header row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Empty cell for the period column
            Box(
                modifier = Modifier.width(44.dp).height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "节次",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            // Day headers
            SchoolDay.entries.forEach { day ->
                val isToday = day == todaySchoolDay
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(40.dp)
                        .then(
                            if (isToday) {
                                Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.primaryContainer)
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day.displayName,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isToday) {
                            MaterialTheme.colorScheme.onPrimaryContainer
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Morning periods (1-4)
        Period.entries.filter { !it.isAfternoon }.forEach { period ->
            ScheduleRow(
                period = period,
                onSubjectClick = { subject, clickedPeriod ->
                    selectedInfo = subject to clickedPeriod
                }
            )
        }

        // Divider between AM and PM
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MaterialTheme.colorScheme.outlineVariant)
        )

        // Afternoon periods (5-7)
        Period.entries.filter { it.isAfternoon }.forEach { period ->
            ScheduleRow(
                period = period,
                onSubjectClick = { subject, clickedPeriod ->
                    selectedInfo = subject to clickedPeriod
                }
            )
        }
    }

    selectedInfo?.let { (subject, period) ->
        AlertDialog(
            onDismissRequest = { selectedInfo = null },
            title = { Text(text = subject.displayName) },
            text = {
                Text(
                    text = "上课：${period.startTime}\n下课：${period.endTime}"
                )
            },
            confirmButton = {
                TextButton(onClick = { selectedInfo = null }) {
                    Text("知道了")
                }
            }
        )
    }
}

@Composable
private fun ScheduleRow(
    period: Period,
    modifier: Modifier = Modifier,
    onSubjectClick: (Subject, Period) -> Unit = { _, _ -> }
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Period indicator (left side)
        PeriodIndicator(period = period)

        // Subject cards for each day
        SchoolDay.entries.forEach { day ->
            val subject = ScheduleRepository.getSubjectForCell(day, period)
            SubjectCard(
                subject = subject,
                period = period,
                onClick = onSubjectClick
            )
        }
    }
}
