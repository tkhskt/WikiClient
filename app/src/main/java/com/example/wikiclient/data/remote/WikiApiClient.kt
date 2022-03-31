package com.example.wikiclient.data.remote

import com.example.wikiclient.data.model.GetArticleListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WikiApiClient {

    @GET("w/api.php?action=query&format=json&formatversion=2&generator=prefixsearch&prop=pageimages%7Cpageterms&piprop=thumbnail&pithumbsize=50&pilimit=10&wbptterms=description")
    suspend fun getArticleList(
        @Query("gpssearch") gpssearch: String,
        @Query("gpslimit") gpslimit: Int,
    ): Response<GetArticleListResponse>
}
