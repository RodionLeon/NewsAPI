package com.example.newsapp.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentNewsListFragmentsBinding
import com.example.newsapp.ui.adapters.NewsAdapter
import com.example.newsapp.ui.adapters.NewsPagerAdapter
import com.example.newsapp.utills.Constants
import com.example.newsapp.viewModels.MainNewsListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewsListFragments : Fragment() {
    private var _binding: FragmentNewsListFragmentsBinding? = null
    private val binding get() = _binding!!
    val viewModel: MainNewsListViewModel by viewModels()
    val adapter = NewsPagerAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewsListFragmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMenu()
        setupRecycler()
        setupObserves()
    }

    private fun setupObserves() {
        lifecycleScope.launch {
            viewModel.getNewsList().observe(viewLifecycleOwner) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
            adapter.loadStateFlow.collect {
                binding.MainNewsProgressBar.isVisible = it.source.refresh is LoadState.Loading
            }
            viewModel.ifProgressBarVisible.observe(viewLifecycleOwner){visible ->
                if(visible){
                    binding.MainNewsProgressBar.visibility = View.VISIBLE
                }
                else{
                    binding.MainNewsProgressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun setupRecycler() {
        binding.newsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.newsRecyclerView.adapter = adapter
    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.settings -> {
                        Log.d("TAG", "settings selected ")
                        true
                    }
                    R.id.favourite -> {
                        Log.d("TAG", "favourite selected ")
                        true
                    }
                    else -> false

                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
