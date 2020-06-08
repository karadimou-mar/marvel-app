package com.example.marvelsquadhero.model.character

import com.google.gson.annotations.SerializedName

data class MarvelCharacterData(
    @SerializedName("results")
    val results: List<MarvelCharacter>?
)