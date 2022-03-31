package com.example.wikiclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wikiclient.databinding.ActivityMainBinding
import com.example.wikiclient.ui.ArticleListAdapter
import com.example.wikiclient.ui.MainViewModel
import com.example.wikiclient.ui.model.Article
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val articleListAdapter = ArticleListAdapter()

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        initArticleList()
        observeArticles()
        initButton()

        viewModel.fetchArticles("Android", 20)
    }

    private fun initButton() {
        binding.button.setOnClickListener {
            val formText = binding.form.text.toString()
            if (formText.isNotEmpty()) {
                viewModel.fetchArticles(formText, 20)
            }
        }
    }

    private fun initArticleList() {
        binding.articleList.apply {
            adapter = articleListAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeArticles() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.articles.collect { articles ->
                    updateArticleList(articles)
                }
            }
        }
    }

    private fun updateArticleList(articles: List<Article>) {
        articleListAdapter.submitList(articles)
    }
}
