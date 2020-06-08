package com.example.marvelsquadhero.database

import androidx.room.TypeConverter
import com.example.marvelsquadhero.model.Thumbnail
import com.example.marvelsquadhero.utils.getThumbnail

class Converters {
    @TypeConverter
    fun stringToThumbnail(value: String?): Thumbnail? {
        val values :List<String> = value?.split(".")!!
        val extension = values.last()
        val url = value.substring(0, value.length - extension.toCharArray().size - 1)
        return Thumbnail(url, extension)
    }

    @TypeConverter
    fun thumbnailToString(thumb: Thumbnail?): String? {
        return thumb?.getThumbnail()
    }
}