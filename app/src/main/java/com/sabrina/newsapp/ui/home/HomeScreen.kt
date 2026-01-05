package com.sabrina.newsapp.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sabrina.newsapp.navigation.NewsRouter


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val articles = viewModel.articlePagingFlow.collectAsLazyPagingItems()
    val savedArticles by viewModel.savedArticles.collectAsStateWithLifecycle()
    val state = viewModel.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Daily News") })
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    CategoryTabs(
                        selectedCategory = state.selectedCategory,
                        onCategorySelected = { newCategory ->
                            viewModel.onCategoryChanged(newCategory)
                        }
                    )
                }

                if (state.isLoading) {
                    item {
                        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                } else {
                    items(state.articles) { article ->
                        val isBookmarked = savedArticles.any { it.url == article.url }
                        TrendingCard(
                            article = article,
                            isBookmarked = isBookmarked,
                            onBookmarkClick = { viewModel.onBookmarkClick(article) },
                            onClick = { NewsRouter.openArticle(context, article.url) },
                            modifier = Modifier.padding(horizontal = 16.dp) // Pass modifier
                        )
                    }
                }

                item {
                    Text(
                        text = "Latest News",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )
                }

                items(count = articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        val isBookmarked = savedArticles.any { it.url == article.url }
                        TrendingCard(
                            article = article,
                            isBookmarked = isBookmarked,
                            onBookmarkClick = { viewModel.onBookmarkClick(article) },
                            onClick = { NewsRouter.openArticle(context, article.url) },
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                if (articles.loadState.append is LoadState.Loading) {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(30.dp))
                        }
                    }
                }
            }

            if (articles.loadState.refresh is LoadState.Error) {
                val error = articles.loadState.refresh as LoadState.Error
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = error.error.localizedMessage ?: "Unknown Error")
                }
            }
            }
        }
}
