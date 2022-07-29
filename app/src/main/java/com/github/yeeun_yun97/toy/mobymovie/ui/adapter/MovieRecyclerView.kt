package com.github.yeeun_yun97.toy.mobymovie.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseAdapter
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseViewHolder
import com.github.yeeun_yun97.toy.mobymovie.data.model.MovieData
import com.github.yeeun_yun97.toy.mobymovie.databinding.ItemMovieBinding

class MovieRecyclerAdapter(private val openOperation: (String) -> Unit) :
    YnBaseAdapter<MovieData, MovieViewHolder>() {
    override fun onBindViewHolder(holder: MovieViewHolder, item: MovieData, position: Int) {
        holder.setItem(item, openOperation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }
}

class MovieViewHolder(binding: ItemMovieBinding) : YnBaseViewHolder<ItemMovieBinding>(binding) {
    fun setItem(item: MovieData, openOperation: (String) -> Unit) {
        binding.item = item
        binding.root.setOnClickListener {
            openOperation(item.movieUrl)
            Log.d("링크", item.movieUrl)
        }
    }
}