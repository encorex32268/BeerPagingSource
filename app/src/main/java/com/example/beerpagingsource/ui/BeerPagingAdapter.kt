package com.example.beerpagingsource.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.beerpagingsource.databinding.ItemBeerViewBinding
import com.example.beerpagingsource.domain.Beer

class BeerPagingAdapter : PagingDataAdapter<Beer , BeerPagingAdapter.BeerViewHolder>(diffCallback = diffCallBack){
    companion object{
        val diffCallBack = object: DiffUtil.ItemCallback<Beer>() {
            override fun areItemsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Beer, newItem: Beer): Boolean = oldItem == newItem
        }
    }
    inner class BeerViewHolder(
        private val binding : ItemBeerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun toBind(beer: Beer?) {
            beer?.let {
                binding.apply {
                    beerName.text = beer.name
                    beerDescriotion.text = beer.description
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BeerViewHolder, position: Int) {
        val beer = getItem(position)
        holder.toBind(beer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerViewHolder {
        return BeerViewHolder(
            ItemBeerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }
}