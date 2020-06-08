package com.example.marvelsquadhero.view.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.marvelsquadhero.model.character.MarvelCharacter

class MarvelCharacterDataSourceFactory: DataSource.Factory<Int, MarvelCharacter>() {

    val itemLiveDataSource = MutableLiveData<PageKeyedDataSource<Int, MarvelCharacter>>()

    override fun create(): DataSource<Int, MarvelCharacter> {
        val itemDataSource = MarvelCharacterDataSource()
        itemLiveDataSource.postValue(itemDataSource)
        return itemDataSource
    }
}