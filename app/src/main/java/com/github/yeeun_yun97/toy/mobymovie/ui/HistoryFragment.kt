package com.github.yeeun_yun97.toy.mobymovie.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentHistoryBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.HistoryRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel
import kotlinx.coroutines.launch

class HistoryFragment : DataBindingBasicFragment<FragmentHistoryBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()

    override fun layoutId(): Int = R.layout.fragment_history

    override fun onCreateView() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val adapter = HistoryRecyclerAdapter(::navigateAndSearchStart)
        viewModel.historyList.observe(viewLifecycleOwner) {
            adapter.setList(it)
        }
        binding.historyRecyclerView.layoutManager = layoutManager
        binding.historyRecyclerView.adapter = adapter
    }

    private fun navigateAndSearchStart(keyword: String) {
        viewModel.bindingKeyword.postValue(keyword)
        findNavController().navigate(R.id.action_historyFragment_to_mainFragment)
    }
}