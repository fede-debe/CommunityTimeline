package com.example.communitymessages.data.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.communitymessages.data.network.ContentService
import com.example.communitymessages.domain.model.response.TimelineResponse
import com.example.communitymessages.utils.DEFAULT_PAGE_LIMIT
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

class TimelinePagingSource @Inject constructor(
    private val contentService: ContentService,
    private val msgId: String
) : PagingSource<Int, TimelineResponse>() {

    override fun getRefreshKey(state: PagingState<Int, TimelineResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TimelineResponse> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                contentService.getTimeline(msgId, page = position, count = DEFAULT_PAGE_LIMIT)
            val data = response.body()
            val timeline = data ?: emptyList()

            val nextKey = if (timeline.isEmpty()) {
                null
            } else {
                position + 1
            }

            LoadResult.Page(
                data = timeline,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }
}