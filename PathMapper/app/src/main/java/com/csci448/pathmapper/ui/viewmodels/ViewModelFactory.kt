package com.csci448.capra_a3.ui.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.csci448.capra.samodelkin.data.database.Repository

class ViewModelFactory(private val context: Context): ViewModelProvider.NewInstanceFactory() {
    fun getViewModelClass() = ThisViewModel::class.java
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if( modelClass.isAssignableFrom(getViewModelClass()) )
                return modelClass
                    .getConstructor(Repository::class.java, Context::class.java)
                    .newInstance(Repository.getInstance(context), context)
            throw IllegalArgumentException()
    }
}