package com.example.newsapp.repos

import com.example.newsapp.network.ApiInterface

class RemoRepo(private val newsApi: ApiInterface) {
    suspend fun getAllNews(country: String = "ru") =newsApi.getAllNews(country)
}