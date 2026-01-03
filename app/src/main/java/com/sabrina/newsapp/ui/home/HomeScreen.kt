package com.sabrina.newsapp.ui.home

import android.media.Image
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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.sabrina.domain.model.Article
import com.sabrina.domain.repository.NewsRepository
import com.sabrina.newsapp.R
import com.sabrina.newsapp.navigation.NewsRouter
import com.sabrina.newsapp.ui.theme.NewsAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val articles = viewModel.articlePagingFlow.collectAsLazyPagingItems()

    val savedArticles by viewModel.savedArticles.collectAsStateWithLifecycle()


    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Daily News") },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = articles.itemCount) { index ->
                    articles[index]?.let { article ->
                        val isBookmarked = savedArticles.any {it.url == article.url}

                        TrendingCard(
                            article = article,
                            isBookmarked = isBookmarked,
                            onBookmarkClick = {
                                viewModel.onBookmarkClick(article)
                            },
                            onClick = {
                                NewsRouter.openArticle(context, article.url)
                            }
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

            if (articles.loadState.refresh is LoadState.Loading) {
                Column(modifier = Modifier.padding(16.dp)) {
                    repeat(6) {
                        ShimmerTrendingCard()
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
