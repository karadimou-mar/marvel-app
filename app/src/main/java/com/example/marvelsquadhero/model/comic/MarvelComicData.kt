package com.example.marvelsquadhero.model.comic

import com.google.gson.annotations.SerializedName

data class MarvelComicData (
    @SerializedName("results")
    val results: List<MarvelComic>?,
    @SerializedName("total")
    val total: Int?,
    @SerializedName("count")
    val count: Int?
)
