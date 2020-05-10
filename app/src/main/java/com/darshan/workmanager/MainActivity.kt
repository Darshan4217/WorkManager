package com.darshan.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set input data to the request
        val data = Data.Builder()
                .putString(KEY_TASK_DESC, "I m sending work data")
                .build()

        // To specify when the work should be perform
        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()


        //There are two types of request onetime & periodic
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(MyWorkManager::class.java)
                .setInputData(data)
                .setConstraints(constraints)
                .build()

        button.setOnClickListener {
            WorkManager.getInstance(this).enqueue(oneTimeWorkRequest)
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
                .observe(this, Observer {
                    if (it.state.isFinished) {

                        // Get output data from work manager.
                        val outputData = it.outputData
                        val output = outputData.getString(KEY_TASK_OUTPUT)
                        textView.append(output + "\n")
                    }
                    val status = it.state.name
                    textView.append(status + "\n")
                })
    }

    companion object {
        var KEY_TASK_DESC = "KEY_TASK_DESC"
        var KEY_TASK_OUTPUT = "KEY_TASK_OUTPUT"

    }
}
