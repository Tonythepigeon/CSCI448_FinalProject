package com.csci448.capra.samodelkin.data.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.csci448.pathmapper.data.database.Path
import java.util.*
import java.util.concurrent.Executors

class Repository private constructor(private val mainDao: MainDao){
    companion object {
        @Volatile private var INSTANCE: Repository? = null
        fun getInstance(context: Context): Repository {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    val database = MainDatabase.getInstance(context)
                    instance = Repository(database.mainDao)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
    private val executor = Executors.newSingleThreadExecutor()
    fun addPath(path: Path) {
        executor.execute {
            mainDao.addPath(path)
        }
    }
    fun getPaths(): LiveData<List<Path>> = mainDao.getPaths()
    fun getPath(id: UUID): LiveData<Path?> = mainDao.getPath(id)
}