package com.example.marvelsquadhero.model.response

import com.example.marvelsquadhero.model.character.MarvelCharacterData
import com.google.gson.annotations.SerializedName

data class MarvelCharacterResponse(
    @SerializedName("code")
    var code: String? = null,

    @SerializedName("status")
    var status: String? = null,

    @SerializedName("data")
    var data: MarvelCharacterData
)