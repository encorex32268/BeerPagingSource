package com.example.beerpagingsource

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.beerpagingsource.data.remote.BeerApi
import com.example.beerpagingsource.domain.Beer
import com.example.beerpagingsource.domain.mapper.toBeer
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class BeerPagingSource @Inject constructor(
    private val beerApi: BeerApi
) : PagingSource<Int,Beer>(){

    private var isGetOnce = true

    override fun getRefreshKey(state: PagingState<Int, Beer>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Beer> {
        val nextPageNumber = params.key ?:1
        return try {
            if (isGetOnce){
                isGetOnce = false
                val result = beerApi.getBeers(nextPageNumber,10)
                Log.d("TAG", "load: ${result.size}}")
                LoadResult.Page(
                    data = result.map { it.toBeer() },
                    prevKey = if (nextPageNumber == 0) null else nextPageNumber - 1,
                    nextKey = if (result.isEmpty()) null else nextPageNumber + 1
                )
            }else{
                LoadResult.Error(
                    Exception("just get once")
                )
            }

        }catch (e : Exception){
            LoadResult.Error(e)
        }catch (e : IOException){
            LoadResult.Error(e)
        }catch (e : HttpException){
            LoadResult.Error(e)
        }
    }
}