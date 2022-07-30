package com.github.yeeun_yun97.toy.mobymovie.ui.fragment

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.yeeun_yun97.clone.ynmodule.ui.component.YnConfirmBaseDialogFragment
import com.github.yeeun_yun97.clone.ynmodule.ui.fragment.DataBindingBasicFragment
import com.github.yeeun_yun97.toy.mobymovie.R
import com.github.yeeun_yun97.toy.mobymovie.databinding.FragmentMainBinding
import com.github.yeeun_yun97.toy.mobymovie.ui.adapter.recycler.MovieRecyclerAdapter
import com.github.yeeun_yun97.toy.mobymovie.ui.tool.RecyclerViewStatusUiTool
import com.github.yeeun_yun97.toy.mobymovie.viewModel.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainFragment : DataBindingBasicFragment<FragmentMainBinding>() {
    private val viewModel: SearchViewModel by activityViewModels()
    private var _loading = false
    private lateinit var recyclerViewUiTool: RecyclerViewStatusUiTool

    override fun layoutId(): Int = R.layout.fragment_main
    override fun onCreateView() {
        binding.viewModel = viewModel
        initRecyclerView()
        recyclerViewUiTool = RecyclerViewStatusUiTool(
            binding.resultRecyclerView,
            binding.resultListShimmer,
            binding.emptyGroup
        )
        binding.searchBtnImageView.setOnClickListener { searchStart() }
        binding.historyBtnImageView.setOnClickListener { navigateToHistory() }
    }

    override fun onStart() {
        super.onStart()
        searchStart()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        val adapter = MovieRecyclerAdapter(::openLink)
        viewModel.bindingKeyword.observe(viewLifecycleOwner) {
            /* 스크롤 시 계속 로드하는 기능을 넣고자 하는데,
            표시된 목록을 기준으로 계속 로드될 지,
            입력되어 있는 키워드를 기준으로 계속 로드될지가 모호하므로,
            키워드가 달라지면 목록을 없애기로 하였다.*/
            if (adapter.itemCount != 0) {
                recyclerViewUiTool.setEmptyStatus()
                adapter.setList(listOf())
            }
        }
        viewModel.movieList.observe(viewLifecycleOwner) {
            lifecycleScope.launch(Dispatchers.Main){
                adapter.setList(it)
                delay(1200)

                if(it.isNullOrEmpty()) recyclerViewUiTool.setEmptyStatus()
                else recyclerViewUiTool.setLoadedStatus()
            }
        }
        binding.resultRecyclerView.layoutManager = layoutManager
        binding.resultRecyclerView.adapter = adapter
        binding.resultRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrolledPosition = layoutManager.findLastCompletelyVisibleItemPosition()
                val isScrolledToEnd = scrolledPosition == adapter.itemCount - 1
                if (!_loading && isScrolledToEnd) {
                    loadNext()
                }
            }
        })
    }

    private fun showInternetErrorDialog(result: Int) {
        YnConfirmBaseDialogFragment(
            "네트워크 실패 ($result)",
            "데이터 로드에 실패하였습니다.",
            null
        ).show(childFragmentManager, "InternetError")
    }

    private fun searchStart() {
        if (!viewModel.isKeywordNullOrEmpty()) {
            recyclerViewUiTool.setLoadingStatus()
            viewModel.saveKeywordToHistory()
            viewModel.searchStart(::showInternetErrorDialog)
        }else{
            recyclerViewUiTool.setEmptyStatus()
        }
    }

    private fun loadNext() {
        startLoading()
        viewModel.loadMore(::showInternetErrorDialog, ::finishLoading)
    }

    private fun startLoading() {
        _loading = true
    }

    private fun finishLoading() {
        _loading = false
    }

    private fun navigateToHistory() {
        findNavController().navigate(R.id.action_mainFragment_to_historyFragment)
    }

    private fun openLink(url: String) {
        if (url.isNotEmpty()) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }
    }


}