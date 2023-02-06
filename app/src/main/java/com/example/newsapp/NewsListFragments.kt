package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.ui.NewsAdapter
import com.example.newsapp.utills.Constants


class NewsListFragments : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_list_fragments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupMenu()
        val rv = view.findViewById<RecyclerView>(R.id.newsRecyclerView)
        rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv.adapter = NewsAdapter(Constants.newsItemDataStub, requireContext())

    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId){
                    R.id.settings -> {
                        Log.d("TAG", "settings selected ")
                        true
                    }
                    R.id.favourite ->{
                        Log.d("TAG", "favourite selected ")
                        true
                    }
                    else -> false

                }
            }
        },viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}