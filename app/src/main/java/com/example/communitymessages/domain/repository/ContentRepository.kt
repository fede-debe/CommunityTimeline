package com.example.communitymessages.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.communitymessages.data.ContentRemoteDataSource
import com.example.communitymessages.data.ContentService
import com.example.communitymessages.data.TimelinePagingSource
import com.example.communitymessages.domain.model.response.Resource
import com.example.communitymessages.domain.model.response.Resource.Status.*
import com.example.communitymessages.domain.model.response.TimelineResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ContentRepository @Inject constructor(
    private val contentRemoteDataSource: ContentRemoteDataSource,
    private val contentService: ContentService
) {

    fun getTimeline(id: String): LiveData<PagingData<TimelineResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TimelinePagingSource(contentService, id) }
        ).liveData
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
}