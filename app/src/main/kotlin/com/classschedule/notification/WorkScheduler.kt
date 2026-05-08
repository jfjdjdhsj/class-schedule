package com.classschedule.notification

import android.content.Context
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

object WorkScheduler {
    private const val MORNING_WORK_NAME = "course_morning_notification"
    private const val AFTERNOON_WORK_NAME = "course_afternoon_notification"

    fun scheduleDailyNotifications(context: Context) {
        scheduleMorning(context)
        scheduleAfternoon(context)
    }

    private fun scheduleMorning(context: Context) {
        val delay = initialDelayFor(7, 30)
        val request = PeriodicWorkRequestBuilder<ScheduleNotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay)
            .setConstraints(Constraints.Builder().build())
            .setInputData(
                Data.Builder()
                    .putString(ScheduleNotificationWorker.KEY_TYPE, ScheduleNotificationWorker.TYPE_MORNING)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            MORNING_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun scheduleAfternoon(context: Context) {
        val delay = initialDelayFor(13, 30)
        val request = PeriodicWorkRequestBuilder<ScheduleNotificationWorker>(1, TimeUnit.DAYS)
            .setInitialDelay(delay)
            .setConstraints(Constraints.Builder().build())
            .setInputData(
                Data.Builder()
                    .putString(ScheduleNotificationWorker.KEY_TYPE, ScheduleNotificationWorker.TYPE_AFTERNOON)
                    .build()
            )
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            AFTERNOON_WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    private fun initialDelayFor(hour: Int, minute: Int): Duration {
        val now = LocalDateTime.now()
        var next = now.withHour(hour).withMinute(minute).withSecond(0).withNano(0)
        if (!next.isAfter(now)) {
            next = next.plusDays(1)
        }
        return Duration.between(now, next)
    }
}
