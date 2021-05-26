package com.intermedia.challenge.data.services

import com.google.gson.annotations.SerializedName
import com.intermedia.challenge.data.models.Character
import com.intermedia.challenge.data.models.Comic
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface ComicService {

        @GET("comics")
        suspend fun getComics(
            @QueryMap auth: HashMap<String, String>,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int,
            @Query("orderBy") onSaleDate : String,
            @Query("startYear") startYear : Int

        ): Response<ComicResponse>

    data class ComicResponse(
        val code: Int = 0,
        @SerializedName("data")
        val comicList: ComicList
    )

    data class ComicList(
        @SerializedName("results")
        val comics: List<Comic>
    )
}