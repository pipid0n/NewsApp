package com.example.newsapp.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.api.NewsRepository
import com.example.newsapp.models.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: NewsRepository): ViewModel() {

    init {
        getSavedArticles()
    }

    fun getSavedArticles() = viewModelScope.launch(Dispatchers.IO) {
        val res = repository.getFavoriteArticles()
        println("DB size is ${res.size}")
        repository.getFavoriteArticles()
    }

    fun saveFavoriteArticle(article: Article) = viewModelScope.launch(Dispatchers.IO) {
        repository.addToFavorites(article = article)
    }
}