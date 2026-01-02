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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel(){

    var state by mutableStateOf(HomeState())
        private set

    val articlePagingFlow: Flow<PagingData<Article>> = repository.getPagedNews().cachedIn(viewModelScope)


    init {
        getTrendingNews()
    }

    fun getTrendingNews(){
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = null)
            try {
                val result = repository.getTrendingNews()
                state = state.copy(
                    articles = result,
                    isLoading = false
                )
            }catch (e: Exception){
                state = state.copy(
                    isLoading = false,
                    error = "Could not fetch news. Check your connection."
                )
            }
        }
    }
}