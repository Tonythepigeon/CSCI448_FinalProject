package com.csci448.pathmapper.data.database

import androidx.compose.ui.graphics.Color
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
    var startLat: Double?,
    var endLat: Double?,
    var startLng: Double?,
    var endLng: Double?,
    var startTime: String,
    var endTime: String,
    var color: String,
    @PrimaryKey val id: UUID = UUID.randomUUID()) : Serializable