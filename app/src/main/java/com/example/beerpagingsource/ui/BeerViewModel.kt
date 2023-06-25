package com.example.beerpagingsource.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.beerpagingsource.BeerPagingSource
import com.example.beerpagingsource.data.remote.BeerApi
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BeerViewModel @Inject constructor(
    private val beerApi: BeerApi
) : ViewModel() {

    var beerFlow = Pager(
        config = PagingConfig(pageSize = 5),
        pagingSourceFactory = {
            BeerPagingSource(
                beerApi
            )
        }
    ).flow.cachedIn(viewModelScope)
}