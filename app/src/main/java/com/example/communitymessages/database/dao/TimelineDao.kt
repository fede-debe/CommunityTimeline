package com.example.communitymessages.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.communitymessages.database.entities.DatabaseMessage

@Dao
interface TimelineDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeline(list: List<DatabaseMessage>)

    @Query("SELECT * FROM timeline ORDER BY score ASC")
    fun pagingSource(): PagingSource<Int, DatabaseMessage>

    @Query("DELETE FROM timeline")
    suspend fun deleteTimeline()
}