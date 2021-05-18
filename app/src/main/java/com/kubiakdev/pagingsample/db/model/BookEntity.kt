package com.kubiakdev.pagingsample.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem

@Entity(tableName = BookEntity.TABLE_NAME)
data class BookEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) override val id: String,
    @ColumnInfo(name = COLUMN_TITLE) override val title: String,
    @ColumnInfo(name = COLUMN_AUTHOR) override val author: String,
    @ColumnInfo(name = COLUMN_DESCRIPTION) override val description: String,
    @ColumnInfo(name = COLUMN_IMAGE_IN_BASE_64) override val imageInBase64: String
) : BookItem {

    companion object {
        const val TABLE_NAME = "book"
        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_IMAGE_IN_BASE_64 = "imageInBase64"
    }
}