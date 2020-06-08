package com.example.marvelsquadhero.model.character

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.marvelsquadhero.model.Thumbnail
import com.google.gson.annotations.SerializedName

@Entity(tableName = "MarvelTable")
data class MarvelCharacter(

    @PrimaryKey
    //@ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Int,

    @ColumnInfo(name = "marvel_name")
    //@Ignore
    @SerializedName("name")
    val name: String,

    @ColumnInfo(name = "marvel_thumbnail")
    //@Ignore
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,

    @ColumnInfo(name = "marvel_description")
    //@Ignore
    @SerializedName("description")
    val description: String


//    @ColumnInfo(name = "is_hired")
//    val isHired: Boolean
)
{
//    constructor() : this(0,"",Thumbnail("",""),"")
//    @PrimaryKey(autoGenerate = true)
//    var heroId: Int = 0
}