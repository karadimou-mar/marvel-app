package com.example.marvelsquadhero.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.marvelsquadhero.model.character.MarvelCharacter

@Database(entities = [MarvelCharacter::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MarvelDatabase: RoomDatabase() {
    abstract fun marvelDao(): MarvelDao

    companion object {
        @Volatile private var instance: MarvelDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            MarvelDatabase::class.java,
            "marvel.db"
        ).build()
    }
}