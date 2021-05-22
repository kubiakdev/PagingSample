package com.kubiakdev.pagingsample.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = RemoteKeyEntity.TABLE_NAME)
data class RemoteKeyEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_NEXT_KEY) val nextKey: Long = 0L
) {

    companion object {
        const val TABLE_NAME = "remote_key"
        const val COLUMN_NEXT_KEY = "next_key"
    }
}