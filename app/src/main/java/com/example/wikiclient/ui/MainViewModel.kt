package com.example.wikiclient.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wikiclient.data.WikiRepository
import com.example.wikiclient.data.WikiRepositoryImpl
import com.example.wikiclient.data.model.GetArticleListResponse
import com.example.wikiclient.ui.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val repository: WikiRepository = WikiRepositoryImpl()
) : ViewModel() {

    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private var job: Job? = null

    fun fetchArticles(keyword: String, limit: Int) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                repository.getArticles(keyword, limit)
            }.onSuccess { response ->
                if (response != null) postArticles(response)
            }.onFailure {
                Log.e("Fetch Failure", it.toString())
            }
        }
    }

    private fun postArticles(response: GetArticleListResponse) {
        _articles.value = response.query.pages.map {
            Article(
                id = it.pageid,
                title = it.title,
                thumbnailUrl = it.thumbnail?.source ?: "",
            )
        }
    }
}
