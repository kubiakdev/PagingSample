package com.kubiakdev.pagingsample.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kubiakdev.pagingsample.databinding.ActivityMainBinding
import com.kubiakdev.pagingsample.ui.main.adapter.MainItemsAdapter
import com.kubiakdev.pagingsample.ui.main.adapter.MainLoadingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        binding.recyclerView.adapter = pagingAdapter.withLoadStateAdapters(mainLoadAdapter)

        lifecycleScope.launch {
            viewModel.books.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
        footer: LoadStateAdapter<*>
    ): ConcatAdapter {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
        }

        return ConcatAdapter(this, footer)
    }
}