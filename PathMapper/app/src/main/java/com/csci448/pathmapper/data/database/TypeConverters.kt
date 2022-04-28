package com.csci448.capra.samodelkin.data.database

import androidx.room.TypeConverter
import com.google.android.gms.maps.model.LatLng
import java.lang.reflect.Type
import java.util.*
import com.google.gson.Gson


class TypeConverters {
    @TypeConverter
    fun fromUUID(uuid: UUID?) = uuid?.toString()
    @TypeConverter
    fun toUUID(uuidString: String?) = UUID.fromString(uuidString)

    @TypeConverter
    fun listToJsonString(value: List<LatLng>?): String? {
        return if (value == null) null else Gson().toJson(value)
    }
    @TypeConverter
    fun jsonStringToList(value: String?): List<LatLng>?{
        return if(value == null) null else Gson().fromJson(value, Array<LatLng>::class.java).toList()
    }

}
