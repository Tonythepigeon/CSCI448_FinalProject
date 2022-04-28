package com.csci448.pathmapper.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.util.*
import java.io.Serializable

@Entity(tableName = "paths")
data class Path(
    var date: String,
    var length: String,
    var coordinates: List<LatLng>,
    @PrimaryKey val id: UUID = UUID.randomUUID()) : Serializable