package com.example.wikiclient.data

import com.example.wikiclient.data.model.GetArticleListResponse
import com.example.wikiclient.data.remote.WikiApiClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

interface WikiRepository {
    suspend fun getArticles(keyword: String, limit: Int): GetArticleListResponse?
}

class WikiRepositoryImpl : WikiRepository {

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ja.wikipedia.org/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val wikiService = retrofit.create(WikiApiClient::class.java)

    override suspend fun getArticles(keyword: String, limit: Int): GetArticleListResponse? {
        return wikiService.getArticleList(keyword, limit).body()
    }
}
