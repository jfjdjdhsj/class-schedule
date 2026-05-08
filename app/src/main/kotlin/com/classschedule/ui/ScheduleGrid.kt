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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.classschedule.data.ScheduleRepository
import com.classschedule.model.Period
import com.classschedule.model.SchoolDay
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun ScheduleGrid(modifier: Modifier = Modifier) {
    val horizontalScrollState = rememberScrollState()
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
            ScheduleRow(period = period)
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
            ScheduleRow(period = period)
        }
    }
}

@Composable
private fun ScheduleRow(
    period: Period,
    modifier: Modifier = Modifier
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
            SubjectCard(subject = subject)
        }
    }
}
