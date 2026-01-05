package com.sabrina.newsapp.ui.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sabrina.newsapp.R
import com.sabrina.newsapp.navigation.NewsRouter
import com.sabrina.newsapp.ui.search.SearchAppBar
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val articles = viewModel.articlePagingFlow.collectAsLazyPagingItems()
    val savedArticles by viewModel.savedArticles.collectAsStateWithLifecycle()
    val state = viewModel.state

    val isRefreshing = articles.loadState.refresh is LoadState.Loading || state.isLoading

    Scaffold(
        topBar = {
            if (state.isSearchWidgetOpened) {
                SearchAppBar(
                    text = state.searchText,
                    onTextChange = { viewModel.onSearchQueryChanged(it) },
                    onCloseClicked = { viewModel.onSearchIconClicked() },
                    onSearchClicked = { viewModel.searchNews(it) }
                )
            } else {
                CenterAlignedTopAppBar(
                    title = { Text("Daily News") },
                    actions = {
                        IconButton(onClick = { viewModel.onSearchIconClicked() }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        PullToRefreshBox(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            isRefreshing = isRefreshing,
            onRefresh = {
                articles.refresh()
                viewModel.refreshAll()
            }
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (!state.isSearchWidgetOpened) {
                    stickyHeader {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.background,
                            tonalElevation = 2.dp
                        ) {
                            CategoryTabs(
                                selectedCategory = state.selectedCategory,
                                onCategorySelected = { newCategory ->
                                    viewModel.onCategoryChanged(newCategory)
                                }
                            )
                        }
                    }

                    if (state.isLoading && !isRefreshing) {
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
                                onClick = {val encodedUrl = URLEncoder.encode(article.url, "UTF-8")
                                    navController.navigate("details/$encodedUrl")},
                                modifier = Modifier.padding(horizontal = 16.dp)
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
                } else {
                    item {
                        Text(
                            text = if (state.searchText.isEmpty()) "Search for news..." else "Results for '${state.searchText}'",
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }

                items(count = articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        val isBookmarked = savedArticles.any { it.url == article.url }
                        TrendingCard(
                            article = article,
                            isBookmarked = isBookmarked,
                            onBookmarkClick = { viewModel.onBookmarkClick(article) },
                            onClick = {val encodedUrl = URLEncoder.encode(article.url, "UTF-8")
                        navController.navigate("details/$encodedUrl")},
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )
                    }
                }

                val isListEmpty = articles.itemCount == 0 && articles.loadState.refresh is LoadState.NotLoading
                if (isListEmpty) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillParentMaxSize()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(
                                    painter = painterResource(id = R.drawable.no_result),
                                    contentDescription = null,
                                    modifier = Modifier.size(80.dp),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                                Text(
                                    text = "No articles found",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(top = 16.dp)
                                )
                                Text(
                                    text = "Try searching for something else",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
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

            if (articles.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
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
