package com.example.beerpagingsource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import com.example.beerpagingsource.databinding.ActivityMainBinding
import com.example.beerpagingsource.ui.BeerLoadStateAdapter
import com.example.beerpagingsource.ui.BeerPagingAdapter
import com.example.beerpagingsource.ui.BeerViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private val viewModel : BeerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val beerPagingAdapter = BeerPagingAdapter()
        val beerLoadStateAdapter = BeerLoadStateAdapter()

        val concatAdapter = beerPagingAdapter.withLoadStateAdaptersCustom(
            footer = beerLoadStateAdapter
        )


        binding.apply {
            beerecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = concatAdapter

            }
            lifecycleScope.launch {
                viewModel.beerFlow.collectLatest {
                    beerPagingAdapter.submitData(it)
                }
            }

        }
    }
}