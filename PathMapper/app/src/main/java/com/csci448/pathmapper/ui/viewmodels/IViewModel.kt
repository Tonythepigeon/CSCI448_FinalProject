package com.csci448.capra_a3.ui.viewmodels

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.csci448.pathmapper.data.database.Path
import com.google.android.gms.maps.model.LatLng

import java.util.*

abstract class IViewModel() : ViewModel() {
    abstract val pathListLiveData: LiveData<List<Path>>
    abstract val pathIdLiveData: LiveData<UUID>
    abstract val pathLiveData: LiveData<Path?>
    abstract fun addPath(path: Path)
    abstract fun loadPath(ID: UUID)
    abstract fun getNewPath()
    abstract fun deleteAllData()
    val currentLocationLiveData = MutableLiveData<Location?>(null)
    val currentAddressLiveData = MutableLiveData("")
    var thisPath: Path? = null;
    var thisPowerLevel: Int = 10000;
    var thisPassData = mutableSetOf<LatLng>()

}