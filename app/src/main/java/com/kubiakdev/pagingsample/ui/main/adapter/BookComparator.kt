package com.kubiakdev.pagingsample.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kubiakdev.pagingsample.api.model.Book

object BookComparator : DiffUtil.ItemCallback<Book>() {

    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean =
        oldItem.title == newItem.title
                && oldItem.author == newItem.author
                && oldItem.description == newItem.description
                && oldItem.imageInBase64 == newItem.imageInBase64

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean = oldItem == newItem
}