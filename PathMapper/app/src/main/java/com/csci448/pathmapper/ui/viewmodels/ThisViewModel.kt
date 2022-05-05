package com.csci448.capra_a3.ui.viewmodels

import android.content.Context
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.csci448.capra.samodelkin.data.database.Repository
import com.csci448.pathmapper.data.database.Path
import java.util.*

class ThisViewModel(
    val mainRepository: Repository,
    context: Context,

) : IViewModel() {

    private val _pathIdLiveData = MutableLiveData<UUID>()
    override val pathListLiveData = mainRepository.getPaths()
    override  val pathIdLiveData = MutableLiveData<UUID>()
    override val pathLiveData: LiveData<Path?> = Transformations.switchMap(_pathIdLiveData) { pathId ->
        mainRepository.getPath(pathId)
    }


    override fun addPath(path: Path) {
        mainRepository.addPath(path)
    }

    override fun loadPath(ID: UUID) {
        _pathIdLiveData.value = ID
    }

    override fun getNewPath() {
        pathIdLiveData.value = mainRepository.getNewPath().value?.id
        pathIdLiveData.value?.let { loadPath(it) }
    }

    override fun deleteAllData() {
        mainRepository.deleteAllData()
    }

}