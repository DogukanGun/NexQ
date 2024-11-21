package com.dag.nexq_app.blockchain

import com.dag.nexq_app.blockchain.data.GoogleSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleSearchService {

    @GET("customsearch/v1")
    suspend fun searchGoogle(
        @Query("key") apiKey: String,
        @Query("cx") cx: String,
        @Query("q") query: String
    ): GoogleSearchResponse

}
