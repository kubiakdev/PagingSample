package com.kubiakdev.pagingsample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kubiakdev.pagingsample.databinding.ActivityMainBinding
import com.kubiakdev.pagingsample.ui.main.adapter.MainItemsAdapter
import com.kubiakdev.pagingsample.ui.main.adapter.MainLoadingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val pagingAdapter = MainItemsAdapter()
        val mainLoadAdapter = MainLoadingAdapter(pagingAdapter::retry)

        binding.recyclerView.adapter = pagingAdapter.withLoadStateAdapters(mainLoadAdapter, binding.swipeRefresh)
        binding.swipeRefresh.setOnRefreshListener { pagingAdapter.refresh() }

        lifecycleScope.launchWhenCreated {
            viewModel.books.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        footer: LoadStateAdapter<*>,
        refreshLayout: SwipeRefreshLayout
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
            refreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading
        }

        return ConcatAdapter(this, footer)
    }
}