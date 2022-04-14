package com.example.communitymessages.database.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.communitymessages.data.network.ContentService
import com.example.communitymessages.database.TimelineDatabase
import com.example.communitymessages.database.entities.DatabaseMessage
import com.example.communitymessages.domain.model.asDatabaseModel
import com.example.communitymessages.utils.COMMUNITY_ID
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class TimelineRemoteMediator(
    private val api: ContentService,
    private val db: TimelineDatabase
) : RemoteMediator<Int, DatabaseMessage>() {

    private val timelineDao = db.getTimelineDao()
    private lateinit var timeline: List<DatabaseMessage>

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DatabaseMessage>
    ): MediatorResult {

        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    lastItem.id
                }
            }

            val response = api.getTimeline(
                id = COMMUNITY_ID,
                page = loadKey
            ).let { response ->
                response.body()?.map { it.asDatabaseModel() }
            }
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    timelineDao.deleteTimeline()
                }
                if (response != null) {
                    timeline = response
                    timelineDao.insertTimeline(timeline)
                }
            }
            MediatorResult.Success(
                endOfPaginationReached = timeline.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}