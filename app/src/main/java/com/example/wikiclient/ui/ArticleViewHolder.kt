package com.example.wikiclient.ui

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.wikiclient.databinding.ItemArticleBinding
import com.example.wikiclient.ui.model.Article

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: Article) {
        binding.title.text = article.title
        binding.thumbnail.load(article.thumbnailUrl)
    }
}
