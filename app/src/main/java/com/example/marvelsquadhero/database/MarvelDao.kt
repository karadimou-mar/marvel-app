package com.example.marvelsquadhero.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.marvelsquadhero.model.character.MarvelCharacter

@Dao
interface MarvelDao {

    @Insert
    fun insertAll( vararg hero: MarvelCharacter)

    @Insert
    fun insert(hero: MarvelCharacter)

    @Query("SELECT * FROM MarvelTable")
    fun getAllHeroes(): List<MarvelCharacter>

    @Query("SELECT * FROM MarvelTable WHERE id =(:heroId)")
    fun getHeroById(heroId: Int): MarvelCharacter

    @Delete
    fun delete(hero: MarvelCharacter)


}