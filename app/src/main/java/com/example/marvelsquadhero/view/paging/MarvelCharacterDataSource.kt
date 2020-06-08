package com.example.marvelsquadhero.view.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.example.marvelsquadhero.model.character.MarvelCharacter
import com.example.marvelsquadhero.model.character.MarvelCharacterData
import com.example.marvelsquadhero.model.response.MarvelCharacterResponse
import com.example.marvelsquadhero.network.MarvelApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelCharacterDataSource : PageKeyedDataSource<Int, MarvelCharacter>() {

    companion object {
        const val LIMIT = 20
        const val OFFSET = 0
    }


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, MarvelCharacter>
    ) {
        val call: Call<MarvelCharacterResponse> = MarvelApiClient.getCharacters(LIMIT, OFFSET)
        call.enqueue(object : Callback<MarvelCharacterResponse> {
            override fun onFailure(call: Call<MarvelCharacterResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED API CONNECTION", t.message)
            }

            override fun onResponse(
                call: Call<MarvelCharacterResponse>,
                response: Response<MarvelCharacterResponse>
            ) {
                val marvelResponse: MarvelCharacterResponse? = response.body()
                val data: MarvelCharacterData? = marvelResponse?.data
                callback.onResult(data?.results!!, null, OFFSET + 20)
            }

        })

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, MarvelCharacter>) {
        val call: Call<MarvelCharacterResponse> = MarvelApiClient.getCharacters(LIMIT, params.key)
        call.enqueue(object : Callback<MarvelCharacterResponse> {
            override fun onFailure(call: Call<MarvelCharacterResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED API CONNECTION", t.message)

            }

            override fun onResponse(
                call: Call<MarvelCharacterResponse>,
                response: Response<MarvelCharacterResponse>
            ) {
                val marvelResponse: MarvelCharacterResponse? = response.body()
                val data: MarvelCharacterData? = marvelResponse?.data
                val key = if (response.body() != null) params.key + LIMIT else null
                callback.onResult(data?.results!!, key)
            }

        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, MarvelCharacter>) {
        val call: Call<MarvelCharacterResponse> = MarvelApiClient.getCharacters(LIMIT, params.key)
        call.enqueue(object: Callback<MarvelCharacterResponse> {
            override fun onFailure(call: Call<MarvelCharacterResponse>, t: Throwable) {
                t.printStackTrace()
                Log.e("FAILED API CONNECTION", t.message)
            }

            override fun onResponse(
                call: Call<MarvelCharacterResponse>,
                response: Response<MarvelCharacterResponse>
            ) {
                val marvelResponse: MarvelCharacterResponse? = response.body()
                val data: MarvelCharacterData? = marvelResponse?.data
                val key = if (response.body()!=null) params.key - LIMIT else null
                callback.onResult(data?.results!!, key)
            }

        })
    }
}