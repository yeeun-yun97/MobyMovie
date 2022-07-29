package com.github.yeeun_yun97.toy.mobymovie.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentMainBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.MovieRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : DataBindingBasicFragment<FragmentMainBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()

    private var loading = false

    override fun layoutId(): Int = R.layout.fragment_main
    override fun onCreateView() {
        binding.viewModel = viewModel
        initRecyclerView()
        binding.searchBtnImageView.setOnClickListener { searchStart() }
        binding.historyBtnImageView.setOnClickListener { navigateToHistory() }
    }

    override fun onStart() {
        super.onStart()
        searchStart()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val adapter = MovieRecyclerAdapter()
        viewModel.bindingKeyword.observe(viewLifecycleOwner) {
            /* 스크롤 시 계속 로드하는 기능을 넣고자 하는데,
            표시된 목록을 기준으로 계속 로드될 지,
            입력되어 있는 키워드를 기준으로 계속 로드될지가 모호하므로,
            키워드가 달라지면 목록을 없애보았다.*/
            if (adapter.itemCount != 0) adapter.setList(listOf())
        }
        viewModel.bindingSearchedList.observe(viewLifecycleOwner) {
            binding.emptyGroup.visibility =
                if (it.isNullOrEmpty()) View.VISIBLE
                else View.GONE
            adapter.setList(it)
        }
        binding.resultRecyclerView.layoutManager = layoutManager
        binding.resultRecyclerView.adapter = adapter

        binding.resultRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!loading && layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    loadNext()
                }
            }
        })
    }

    private fun searchStart() {
        viewModel.saveKeywordToHistory()
        viewModel.searchStart()
    }

    private fun loadNext() {
        loading = true
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.loadMore().join()
            loading = false
        }
    }

    private fun navigateToHistory() {
        findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
    }


}