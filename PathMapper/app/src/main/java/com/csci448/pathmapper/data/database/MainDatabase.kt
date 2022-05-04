package com.csci448.capra.samodelkin.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.csci448.pathmapper.data.database.Path

@Database(version = 4, entities = [Path::class])
//@TypeConverters(TypeConverters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract val mainDao: MainDao

    companion object {
        @Volatile
        private var INSTANCE: MainDatabase? = null
        fun getInstance(context: Context): MainDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context, MainDatabase::class.java,
                        "main-database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}