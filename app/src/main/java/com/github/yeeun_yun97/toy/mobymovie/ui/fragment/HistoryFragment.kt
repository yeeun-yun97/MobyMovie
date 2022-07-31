package com.github.yeeun_yun97.toy.mobymovie.ui.fragment

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentHistoryBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.recycler.HistoryRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.ui.tool.RecyclerViewStatusUiTool
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HistoryFragment : DataBindingBasicFragment<FragmentHistoryBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()
    private lateinit var recyclerViewUiTool: RecyclerViewStatusUiTool

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() = navigateToHome()
    }

    override fun layoutId(): Int = R.layout.fragment_history

    override fun onCreateView() {
        initRecyclerView()
        setOnBackPressedCallback()
        recyclerViewUiTool = RecyclerViewStatusUiTool(
            binding.historyRecyclerView,
            binding.historyListShimmer,
            binding.emptyGroup
        )
    }

    private fun setOnBackPressedCallback() {
        requireActivity().onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, onBackPressedCallback)
    }

    override fun onStart() {
        super.onStart()
        recyclerViewUiTool.setLoadingStatus()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val adapter = HistoryRecyclerAdapter(::setKeywordAndNavigateToHome)
        viewModel.historyList.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Main) {
                adapter.setList(it)
                delay(1200) // wait for recyclerView set items
                if (it.isNullOrEmpty())
                    recyclerViewUiTool.setEmptyStatus()
                else
                    recyclerViewUiTool.setLoadedStatus()
            }
        }
        binding.historyRecyclerView.layoutManager = layoutManager
        binding.historyRecyclerView.adapter = adapter
    }

    private fun setKeywordAndNavigateToHome(keyword: String) {
        viewModel.bindingKeyword.postValue(keyword)
        navigateToHome()
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_historyFragment_to_mainFragment)
    }


}