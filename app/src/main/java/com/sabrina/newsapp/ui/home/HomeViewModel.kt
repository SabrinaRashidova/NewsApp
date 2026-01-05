package com.sabrina.newsapp.ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository
import com.sabrina.newsapp.ui.util.categories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel(){

    var state by mutableStateOf(HomeState())
        private set
    val savedArticles = repository.getSavedArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000),emptyList())

    private val _selectedCategory = MutableStateFlow("general")
    val articlePagingFlow = _selectedCategory.flatMapLatest { category->
        repository.getPagedNews(category)
    }.cachedIn(viewModelScope)

    init {
        getTrendingNews(state.selectedCategory)
    }

    fun getTrendingNews(category: String){
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val result = repository.getTrendingNews(category.lowercase())
                state = state.copy(
                    articles = result,
                    isLoading = false
                )
            }catch (e: Exception){
                state = state.copy(
                    isLoading = false,
                    error = "Failed to load $category news"
                )
            }
        }
    }

    fun onCategoryChanged(newCategory: String){
        _selectedCategory.value = newCategory.lowercase()
        state = state.copy(selectedCategory = newCategory)
        getTrendingNews(newCategory)
    }

    fun onBookmarkClick(article: Article){
        viewModelScope.launch {
            val isAlreadySaved = savedArticles.value.any { it.url == article.url }
            if (isAlreadySaved){
                repository.deleteArticle(article)
            }else{
                repository.upsertArticle(article)
            }
        }
    }
}