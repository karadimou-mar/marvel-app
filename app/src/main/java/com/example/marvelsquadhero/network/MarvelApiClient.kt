package com.example.marvelsquadhero.network

import com.example.marvelsquadhero.model.response.MarvelCharacterResponse
import com.example.marvelsquadhero.model.response.MarvelComicResponse
import com.example.marvelsquadhero.utils.MarvelRequestGenerator
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object MarvelApiClient {

    private const val BASE_URL = "http://gateway.marvel.com/v1/public/"

    private  val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(MarvelApi::class.java)

    fun getCharacters(limit: Int, offset: Int): Call<MarvelCharacterResponse> {
        val generator = MarvelRequestGenerator.createParams()
        return api.getCharacters(generator.timestamp, generator.apiKey, generator.hash, limit, offset)
    }

    fun getComics(characterId: Int): Call<MarvelComicResponse>{
        val generator = MarvelRequestGenerator.createParams()
        return api.getComics(characterId, generator.timestamp, generator.apiKey, generator.hash)
    }
}
