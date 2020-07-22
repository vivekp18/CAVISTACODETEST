package com.example.cavistacodetest.utilities.typeconverters

import androidx.room.TypeConverter
import com.example.cavistacodetest.model.Datum
import com.example.cavistacodetest.model.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

open class ImagesTypeConverters {

    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Image?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Image?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Image?>?): String? {
        return gson.toJson(someObjects)
    }
}