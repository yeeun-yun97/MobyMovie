package com.github.yeeun_yun97.toy.mobymovie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseAdapter
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseViewHolder
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.databinding.ItemMovieBinding

class MovieRecyclerAdapter : YnBaseAdapter<MovieData, MovieViewHolder>() {
    override fun onBindViewHolder(holder: MovieViewHolder, item: MovieData, position: Int) {
        holder.setItem(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}


class MovieViewHolder(binding: ItemMovieBinding) : YnBaseViewHolder<ItemMovieBinding>(binding) {
    fun setItem(item: MovieData) {
        binding.item = item
    }
}