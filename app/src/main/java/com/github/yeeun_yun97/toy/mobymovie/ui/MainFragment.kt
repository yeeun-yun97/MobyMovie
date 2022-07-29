package com.github.yeeun_yun97.toy.mobymovie.ui

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentMainBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.MovieRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel

class MainFragment : DataBindingBasicFragment<FragmentMainBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()

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
        viewModel.bindingSearchedList.observe(viewLifecycleOwner) {
            binding.emptyGroup.visibility =
                if (it.isNullOrEmpty()) View.VISIBLE
                else View.GONE
            adapter.setList(it)
        }
        binding.resultRecyclerView.layoutManager = layoutManager
        binding.resultRecyclerView.adapter = adapter
    }

    private fun searchStart() {
        viewModel.searchStart()
    }

    private fun navigateToHistory() {
        findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
    }


}