package com.example.communitymessages.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.communitymessages.database.entities.DatabaseRemoteKey

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllRemoteKeys(list: List<DatabaseRemoteKey>)

    @Query("SELECT * FROM remote_keys WHERE id = :id")
    suspend fun getAllREmoteKey(id: String): DatabaseRemoteKey?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAllRemoteKeys()

}