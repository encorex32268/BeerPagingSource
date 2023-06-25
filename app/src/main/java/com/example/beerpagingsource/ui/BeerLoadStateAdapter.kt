package com.example.beerpagingsource.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.LoadStates
import androidx.recyclerview.widget.RecyclerView
import com.example.beerpagingsource.databinding.ItemBeerLoadViewBinding

class BeerLoadStateAdapter :  LoadStateAdapter<BeerLoadStateAdapter.BeerLoadViewHolder>() {

    inner class BeerLoadViewHolder(
        private val binding : ItemBeerLoadViewBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun toBind(loadState: LoadState) {
            Log.d("TAG", "toBind: ${loadState}")
            if (loadState is LoadState.Loading){
                binding.progressBar.visibility = View.VISIBLE
            }else{
                binding.progressBar.visibility = View.GONE
            }
        }

    }

    override fun onBindViewHolder(holder: BeerLoadViewHolder, loadState: LoadState) {
        holder.toBind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): BeerLoadViewHolder {
        return BeerLoadViewHolder(
            ItemBeerLoadViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

//    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
//        return loadState is LoadState.Loading || loadState is LoadState.Error ||
//                (loadState is LoadState.NotLoading && loadState.endOfPaginationReached)
//    }


}