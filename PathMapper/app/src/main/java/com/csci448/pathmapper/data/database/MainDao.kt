package com.csci448.capra.samodelkin.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.csci448.pathmapper.data.database.Path
import java.util.*

@Dao
interface MainDao {
    @Insert
    fun addPath(path: Path)

    @Query("SELECT * FROM paths")
    fun getPaths(): LiveData<List<Path>>

    @Query("SELECT * FROM paths WHERE id=(:id)")
    fun getPath(id: UUID): LiveData<Path?>

}