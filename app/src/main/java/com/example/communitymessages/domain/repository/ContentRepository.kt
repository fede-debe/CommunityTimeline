package com.example.communitymessages.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.communitymessages.data.network.ContentRemoteDataSource
import com.example.communitymessages.data.network.ContentService
import com.example.communitymessages.database.TimelineDatabase
import com.example.communitymessages.database.entities.DatabaseMessage
import com.example.communitymessages.database.mediator.TimelineRemoteMediator
import com.example.communitymessages.domain.model.asDatabaseModel
import com.example.communitymessages.domain.model.response.Resource
import com.example.communitymessages.domain.model.response.Resource.Status.*
import com.example.communitymessages.utils.COMMUNITY_ID
import com.example.communitymessages.utils.DEFAULT_PAGE_LIMIT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val contentRemoteDataSource: ContentRemoteDataSource,
    private val contentService: ContentService,
    private val database: TimelineDatabase
) {

    // Here we can use the Mediator inside the repository
    @OptIn(ExperimentalPagingApi::class)
    fun getTimeline(): Flow<PagingData<DatabaseMessage>> {
        val pagingSourceFactory = { database.getTimelineDao().pagingSource() }
        return Pager(
            config = PagingConfig(
                pageSize = DEFAULT_PAGE_LIMIT,
                enablePlaceholders = false
            ),
            remoteMediator = TimelineRemoteMediator(
                api = contentService,
                db = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    suspend fun addToInterest(id: String) = flow {
        emit(Resource.loading(null))
        val response = contentRemoteDataSource.addToInterest(id)
        when (response.status) {
            SUCCESS -> {
                emit(Resource.success(response.data))
            }
            ERROR -> {
                emit(Resource.error(response.errorResponse, null))
            }
            LOADING -> {
                emit(Resource.loading(null))
            }
        }
    }

    suspend fun refreshTimeline() {
        withContext(Dispatchers.IO) {
            val timeline = contentService.getTimeline(COMMUNITY_ID, "1")
            database.getTimelineDao().insertTimeline(*timeline.asDatabaseModel())
        }
    }
}