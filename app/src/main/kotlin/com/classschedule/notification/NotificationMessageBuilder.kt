package com.classschedule.notification

import com.classschedule.data.ScheduleRepository
import com.classschedule.model.SchoolDay

object NotificationMessageBuilder {

    data class NotificationContent(
        val title: String,
        val body: String
    )

    fun buildMorningMessage(day: SchoolDay): NotificationContent {
        val subjects = ScheduleRepository.getMorningSubjects(day)
        val body = if (subjects.isEmpty()) {
            "上午无课"
        } else {
            subjects.joinToString("、") { "${it.displayName}(${it.teacher})" }
        }

        return NotificationContent(
            title = "今日上午课程",
            body = body
        )
    }

    fun buildAfternoonMessage(day: SchoolDay): NotificationContent {
        val subjects = ScheduleRepository.getAfternoonSubjects(day)

        if (day == SchoolDay.FRIDAY && subjects.isEmpty()) {
            return NotificationContent(
                title = "今日下午课程",
                body = "放假了，开心吗"
            )
        }

        val body = if (subjects.isEmpty()) {
            "下午无课"
        } else {
            subjects.joinToString("、") { "${it.displayName}(${it.teacher})" }
        }

        return NotificationContent(
            title = "今日下午课程",
            body = body
        )
    }
}
