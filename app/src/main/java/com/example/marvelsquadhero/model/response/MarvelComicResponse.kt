package com.example.marvelsquadhero.model.response

import com.example.marvelsquadhero.model.comic.MarvelComicData
import com.google.gson.annotations.SerializedName

class MarvelComicResponse (
    @SerializedName("code")
    var code: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("data")
    var data: MarvelComicData
)