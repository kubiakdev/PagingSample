package com.kubiakdev.pagingsample.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kubiakdev.pagingsample.db.model.RemoteKeyEntity

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKeyEntity)

    @Query("SELECT * FROM ${RemoteKeyEntity.TABLE_NAME}")
    suspend fun getRemoteKeys(): List<RemoteKeyEntity>

    @Query("DELETE FROM ${RemoteKeyEntity.TABLE_NAME}")
    suspend fun deleteRemoteKey()
}