package com.example.communitymessages.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.communitymessages.data.network.ContentService
import com.example.communitymessages.database.entities.DatabaseMessage
import com.example.communitymessages.domain.model.asDatabaseModel
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val STARTING_PAGE_INDEX = 1

@OptIn(ExperimentalPagingApi::class)
class TimelinePagingSource(
    private val contentService: ContentService,
    private val msgId: String
) : PagingSource<Int, DatabaseMessage>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DatabaseMessage> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response =
                contentService.getTimeline(msgId, page = page.toString()) // todo fix
            val data = response.body()
            val timeline = data?.map { it.asDatabaseModel() } ?: emptyList()

            val nextKey = if (timeline.isEmpty()) {
                null
            } else {
                page + 1
            }

            LoadResult.Page(
                data = timeline,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DatabaseMessage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}