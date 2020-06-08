package com.example.marvelsquadhero.network

import com.example.marvelsquadhero.model.response.MarvelCharacterResponse
import com.example.marvelsquadhero.model.response.MarvelComicResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarvelApi {

    @GET("characters")
    fun getCharacters(
        @Query("ts") timestamp: Long?,
        @Query("apikey") apiKey: String?,
        @Query("hash") hash: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?
    ): Call<MarvelCharacterResponse>

    @GET("characters/{characterId}/comics")
    fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") timestamp: Long?,
        @Query("apikey") apiKey: String?,
        @Query("hash") hash: String?
    ): Call<MarvelComicResponse>
}