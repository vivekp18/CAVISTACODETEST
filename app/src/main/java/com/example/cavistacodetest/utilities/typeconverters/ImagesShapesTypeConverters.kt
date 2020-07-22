package com.example.cavistacodetest.utilities.typeconverters

import androidx.room.TypeConverter
import com.example.cavistacodetest.model.Datum
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*


open class ImagesShapesTypeConverters {
    var gson = Gson()

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<Datum?>? {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type =
            object : TypeToken<List<Datum?>?>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<Datum?>?): String? {
        return gson.toJson(someObjects)
    }
}