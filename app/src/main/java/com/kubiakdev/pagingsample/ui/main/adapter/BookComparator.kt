package com.kubiakdev.pagingsample.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kubiakdev.pagingsample.api.model.BookResponse
import com.kubiakdev.pagingsample.ui.main.adapter.item.BookItem

object BookComparator : DiffUtil.ItemCallback<BookItem>() {

    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean =
        oldItem.title == newItem.title
                && oldItem.author == newItem.author
                && oldItem.description == newItem.description
                && oldItem.imageInBase64 == newItem.imageInBase64

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean = oldItem == newItem
}