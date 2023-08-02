package com.renatic.app.helper

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class ModelWorker(ctx: Context, params: WorkerParameters): Worker(ctx, params) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}