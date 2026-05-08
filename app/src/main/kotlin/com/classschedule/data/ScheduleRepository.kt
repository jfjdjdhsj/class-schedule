package com.classschedule.data

import com.classschedule.model.*

object ScheduleRepository {

    val schedule: List<ScheduleItem> = listOf(
        // 星期一
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_1, Subject.SHU_JU),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_2, Subject.SHU_JU),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_3, Subject.YING_YU),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_4, Subject.XIN_LI),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_5, Subject.SHU_XUE),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_6, Subject.SHU_XUE),
        ScheduleItem(SchoolDay.MONDAY, Period.PERIOD_7, null),
        // 星期二
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_1, Subject.YING_YU),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_2, Subject.YING_YU),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_3, Subject.SHU_XUE),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_4, Subject.SHU_XUE),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_5, Subject.WANG_YE),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_6, Subject.WANG_YE),
        ScheduleItem(SchoolDay.TUESDAY, Period.PERIOD_7, null),
        // 星期三
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_1, Subject.YU_WEN),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_2, Subject.YU_WEN),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_3, Subject.ZU_ZHUANG),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_4, Subject.ZU_ZHUANG),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_5, Subject.YING_YU),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_6, Subject.SHU_XUE),
        ScheduleItem(SchoolDay.WEDNESDAY, Period.PERIOD_7, Subject.SHU_XUE),
        // 星期四
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_1, Subject.SHE_YING),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_2, Subject.SHE_YING),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_3, Subject.PS),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_4, Subject.PS),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_5, Subject.YU_WEN),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_6, Subject.YU_WEN),
        ScheduleItem(SchoolDay.THURSDAY, Period.PERIOD_7, Subject.ZU_ZHUANG),
        // 星期五
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_1, Subject.YU_WEN),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_2, null),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_3, Subject.YING_YU),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_4, Subject.YING_YU),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_5, null),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_6, null),
        ScheduleItem(SchoolDay.FRIDAY, Period.PERIOD_7, null)
    )

    fun getItem(day: SchoolDay, period: Period): ScheduleItem? =
        schedule.find { it.day == day && it.period == period }

    fun getSubjectForCell(day: SchoolDay, period: Period): Subject? =
        getItem(day, period)?.subject

    fun getMorningSubjects(day: SchoolDay): List<Subject> =
        schedule
            .filter { it.day == day && !it.period.isAfternoon && it.subject != null }
            .mapNotNull { it.subject }

    fun getAfternoonSubjects(day: SchoolDay): List<Subject> =
        schedule
            .filter { it.day == day && it.period.isAfternoon && it.subject != null }
            .mapNotNull { it.subject }
}
