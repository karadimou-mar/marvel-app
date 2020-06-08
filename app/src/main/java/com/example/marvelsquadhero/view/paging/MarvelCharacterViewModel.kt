package com.example.marvelsquadhero.view.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.example.marvelsquadhero.model.character.MarvelCharacter

class MarvelCharacterViewModel: ViewModel() {

    var characterPageList : LiveData<PagedList<MarvelCharacter>>
    var liveDataSource: LiveData<PageKeyedDataSource<Int, MarvelCharacter>>

    init {
        val dataSourceFactory = MarvelCharacterDataSourceFactory()
        liveDataSource = dataSourceFactory.itemLiveDataSource

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(MarvelCharacterDataSource.LIMIT)
            .build()

        characterPageList = LivePagedListBuilder(dataSourceFactory, config).build()
    }
}