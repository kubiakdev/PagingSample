package com.kubiakdev.pagingsample.ui.main.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.pagingsample.api.model.Book
import com.kubiakdev.pagingsample.databinding.ItemBookDetailsBinding

class MainItemsAdapter : PagingDataAdapter<Book, MainItemsAdapter.ViewHolder>(BookComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemBookDetailsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindHolder(getItem(position)!!)
    }

    inner class ViewHolder(private val binding: ItemBookDetailsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindHolder(item: Book) {
            with(binding) {
                titleText.text = "Title: ${item.title}"
                authorText.text = "Author: ${item.author}"
                imageView.setImageBitmap(getBitmap(item.imageInBase64))
                descriptionText.text = "Description: ${item.description}"
            }
        }

        private fun getBitmap(imageBase64: String): Bitmap {
            val byteArray = Base64.decode(imageBase64, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        }
    }
}