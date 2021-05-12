package com.kubiakdev.pagingsample.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.kubiakdev.pagingsample.databinding.ActivityMainBinding
import com.kubiakdev.pagingsample.ui.main.adapter.MainAdapter
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
        val pagingAdapter = MainAdapter()
        binding.recyclerView.adapter = pagingAdapter

        lifecycleScope.launch {
            viewModel.books.collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }

        pagingAdapter.addLoadStateListener { loadStates ->
            binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
            binding.retryButton.isVisible = loadStates.refresh is LoadState.Error
            if (loadStates.refresh is LoadState.Error)
                Toast.makeText(this@MainActivity, "Error occured", Toast.LENGTH_LONG).show()
        }
    }
}