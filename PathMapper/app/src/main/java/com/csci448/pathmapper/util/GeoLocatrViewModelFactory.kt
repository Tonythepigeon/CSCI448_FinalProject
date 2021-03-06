package com.csci448.pathmapper.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class GeoLocatrViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    fun getViewModelClass() = GeoLocatrViewModel::class.java
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if( modelClass.isAssignableFrom(getViewModelClass()) )
            return modelClass.getConstructor().newInstance()
        throw IllegalArgumentException("Unknown ViewModel")
    }
}