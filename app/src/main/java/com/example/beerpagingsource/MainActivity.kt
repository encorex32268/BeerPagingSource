package com.example.beerpagingsource

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AbsListView.OnScrollListener
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE
import androidx.viewpager.widget.PagerAdapter
import com.example.beerpagingsource.databinding.ActivityMainBinding
import com.example.beerpagingsource.domain.util.AppendErrorDialog
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
    private val appendErrorDialog : AppendErrorDialog by lazy {
        AppendErrorDialog()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val beerPagingAdapter = BeerPagingAdapter()
        val beerLoadStateAdapter = BeerLoadStateAdapter(
            onAppendError = {
                showErrorAlert()
            }
        )
        val concatAdapter = beerPagingAdapter.withLoadStateAdaptersCustom(
            footer = beerLoadStateAdapter
        )


        binding.apply {
            beerecyclerView.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(this@MainActivity)
                adapter = concatAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener(){
                    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                        super.onScrollStateChanged(recyclerView, newState)
                        val isSawLastItem = (recyclerView.layoutManager as LinearLayoutManager) .findLastVisibleItemPosition()
                        val items = recyclerView.adapter?.itemCount
                        if (items != null) {
                            if ( (items - 2 )  == isSawLastItem && newState== SCROLL_STATE_IDLE ){
                                beerPagingAdapter.retry()
                            }
                        }

                    }
                })
            }
            lifecycleScope.launch {
                viewModel.beerFlow.collectLatest {
                    beerPagingAdapter.submitData(it)
                }
            }
        }
    }

    private fun showErrorAlert() {
        val supportFragment  = this@MainActivity.supportFragmentManager
        val appendFragment = supportFragment.findFragmentByTag("appendErrorDialog")
        if (appendFragment == null){
            appendErrorDialog.show(this.supportFragmentManager,"appendErrorDialog")
        }else{
            supportFragmentManager.beginTransaction()
                .remove(appendFragment)
            appendErrorDialog.show(this.supportFragmentManager,"appendErrorDialog")

        }

    }
}