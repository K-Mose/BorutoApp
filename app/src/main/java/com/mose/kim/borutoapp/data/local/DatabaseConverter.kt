package com.mose.kim.borutoapp.data.local

import androidx.compose.animation.splineBasedDecay
import androidx.room.TypeConverter


class DatabaseConverter {

    private val separator = ","

    @TypeConverter
    fun convertListToString(list: List<String>): String {
        val stringBuilder = StringBuilder()
        /*list.reduce { acc, s ->
            acc + separator + s
        }*/
        for(item in list) {
            stringBuilder.append(item).append(separator)
        }
        // 마지막에 붙은 separator 제거
        stringBuilder.setLength(stringBuilder.length - separator.length)
        return stringBuilder.toString()
    }

    @TypeConverter
    fun convertStringToList(string: String): List<String> {
        return string.split(separator)
    }
}