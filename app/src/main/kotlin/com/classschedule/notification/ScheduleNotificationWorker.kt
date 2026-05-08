package com.classschedule.notification

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.classschedule.model.SchoolDay
import java.time.DayOfWeek
import java.time.LocalDate

class ScheduleNotificationWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        NotificationHelper.createChannel(applicationContext)

        val type = inputData.getString(KEY_TYPE) ?: return Result.success()
        val today = mapTodayToSchoolDay() ?: return Result.success()

        val content = when (type) {
            TYPE_MORNING -> NotificationMessageBuilder.buildMorningMessage(today)
            TYPE_AFTERNOON -> NotificationMessageBuilder.buildAfternoonMessage(today)
            else -> return Result.success()
        }

        val notificationId = if (type == TYPE_MORNING) 1001 else 1002
        NotificationHelper.showNotification(applicationContext, content.title, content.body, notificationId)
        return Result.success()
    }

    private fun mapTodayToSchoolDay(): SchoolDay? = when (LocalDate.now().dayOfWeek) {
        DayOfWeek.MONDAY -> SchoolDay.MONDAY
        DayOfWeek.TUESDAY -> SchoolDay.TUESDAY
        DayOfWeek.WEDNESDAY -> SchoolDay.WEDNESDAY
        DayOfWeek.THURSDAY -> SchoolDay.THURSDAY
        DayOfWeek.FRIDAY -> SchoolDay.FRIDAY
        else -> null
    }

    companion object {
        const val KEY_TYPE = "type"
        const val TYPE_MORNING = "morning"
        const val TYPE_AFTERNOON = "afternoon"
    }
}
