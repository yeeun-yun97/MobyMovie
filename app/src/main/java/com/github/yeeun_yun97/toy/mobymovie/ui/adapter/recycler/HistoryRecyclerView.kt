package com.github.yeeun_yun97.toy.mobymovie.ui.adapter.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseAdapter
import com.github.yeeun_yun97.clone.ynmodule.ui.adapter.YnBaseViewHolder
import com.github.yeeun_yun97.toy.mobymovie.data.model.History
import com.github.yeeun_yun97.toy.mobymovie.databinding.ItemHistoryBinding

class HistoryRecyclerAdapter(
    private val searchOperation: (String) -> Unit
) : YnBaseAdapter<History, HistoryViewHolder>() {
    override fun onBindViewHolder(holder: HistoryViewHolder, item: History, position: Int) {
        holder.setItem(item, searchOperation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoryViewHolder(binding)
    }


}

class HistoryViewHolder(binding: ItemHistoryBinding) :
    YnBaseViewHolder<ItemHistoryBinding>(binding) {
    fun setItem(item: History, searchOperation: (String) -> Unit) {
        binding.keyword = item.keyword
        binding.root.setOnClickListener { searchOperation(item.keyword) }
    }


}