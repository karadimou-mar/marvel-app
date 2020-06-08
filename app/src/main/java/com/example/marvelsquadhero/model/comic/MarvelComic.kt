package com.example.marvelsquadhero.model.comic

import com.example.marvelsquadhero.model.Thumbnail
import com.google.gson.annotations.SerializedName

data class MarvelComic (
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail
)