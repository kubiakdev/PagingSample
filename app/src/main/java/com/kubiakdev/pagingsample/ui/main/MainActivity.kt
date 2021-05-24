package com.kubiakdev.pagingsample.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.kubiakdev.pagingsample.R
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
        binding.errorView.setOnRetryButtonClick { pagingAdapter.retry() }

        binding.errorView.setErrorImageRes(R.drawable.ic_error_outline)

        lifecycleScope.launchWhenCreated {
            viewModel.books.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        footer: LoadStateAdapter<*>,
        refreshLayout: SwipeRefreshLayout
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
            refreshLayout.isRefreshing = loadStates.refresh is LoadState.Loading && viewModel.dataAlreadyLoaded
            binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading && !viewModel.dataAlreadyLoaded
            binding.errorView.showOrHide(loadStates.refresh is LoadState.Error && !viewModel.dataAlreadyLoaded)
            if (loadStates.refresh is LoadState.Error && viewModel.dataAlreadyLoaded) {
                Toast.makeText(this@MainActivity, R.string.error_message, Toast.LENGTH_SHORT).show()
            }
        }

        return ConcatAdapter(this, footer)
    }
}