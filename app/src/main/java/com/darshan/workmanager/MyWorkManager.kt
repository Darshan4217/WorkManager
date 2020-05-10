package com.darshan.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkManager(context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {

    override fun doWork(): Result {
        val data = inputData
        val desc = data.getString(KEY_TASK_DESC)

        displayNotification("Hey I m on your work", desc!!)

        // Set output data
        val outputData = Data.Builder()
            .putString(KEY_TASK_OUTPUT, "Task finished successfully")
            .build()

        return  Result.success(outputData)
    }

    private fun displayNotification(task: String, desc: String) {
        //After android nodgute need to create notification channels

        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "darshan",
                "darshanChannel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            manager.createNotificationChannel(notificationChannel)

            val builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, "darshan")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher)

            manager.notify(1, builder.build())
        }

    }

    companion object {
        var  KEY_TASK_DESC = "KEY_TASK_DESC"
        var  KEY_TASK_OUTPUT = "KEY_TASK_OUTPUT"

    }
}