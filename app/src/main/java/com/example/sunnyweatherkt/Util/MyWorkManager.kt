package com.example.sunnyweatherkt.Util

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkManager(context:Context,params:WorkerParameters): Worker(context,params) {

    override fun doWork(): Result {

        return Result.success()
    }
}