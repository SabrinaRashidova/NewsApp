package com.sabrina.newsapp.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel(){

    val savedArticles = repository.getSavedArticles()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onBookmarkClick(article: Article) {
        viewModelScope.launch {
            repository.deleteArticle(article)
        }
    }
}