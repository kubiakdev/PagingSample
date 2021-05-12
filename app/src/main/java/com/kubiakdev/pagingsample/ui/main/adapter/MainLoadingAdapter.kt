package com.kubiakdev.pagingsample.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.pagingsample.databinding.ItemLoadingItemBinding

class MainLoadingAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<MainLoadingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = ViewHolder(
        ItemLoadingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bindHolder(loadState)
    }

    inner class ViewHolder(
        private val binding: ItemLoadingItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindHolder(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                retryButton.setOnClickListener { retry() }
            }
        }
    }
}