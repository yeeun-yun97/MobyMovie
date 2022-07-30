package com.github.yeeun_yun97.toy.mobymovie.ui

import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentHistoryBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.HistoryRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel

class HistoryFragment : DataBindingBasicFragment<FragmentHistoryBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()

    override fun layoutId(): Int = R.layout.fragment_history

    override fun onCreateView() {
        initRecyclerView()

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToHome()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,callback)
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val adapter = HistoryRecyclerAdapter(::navigateAndSearchStart)
        viewModel.historyList.observe(viewLifecycleOwner) {
            binding.emptyGroup.visibility =
                if (it.isNullOrEmpty()) View.VISIBLE
                else View.GONE
            adapter.setList(it)
        }
        binding.historyRecyclerView.layoutManager = layoutManager
        binding.historyRecyclerView.adapter = adapter
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_historyFragment_to_mainFragment)
    }

    private fun navigateAndSearchStart(keyword: String) {
        viewModel.bindingKeyword.postValue(keyword)
        navigateToHome()
    }
}