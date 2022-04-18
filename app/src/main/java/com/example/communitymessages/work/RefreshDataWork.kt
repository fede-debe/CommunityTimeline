package com.example.communitymessages.work

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.communitymessages.database.TimelineDatabase
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class RefreshDataWork @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val database: TimelineDatabase
) : Worker(appContext, workerParams) {

    // TODO
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}