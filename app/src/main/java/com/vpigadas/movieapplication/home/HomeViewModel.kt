package com.vpigadas.movieapplication.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.movieapplication.models.LocalCollections
import com.vpigadas.movieapplication.utils.network.ApiClient

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val streamData: MutableLiveData<LocalCollections> = MutableLiveData()
    private val streamError: MutableLiveData<Exception> = MutableLiveData()

    init {
        ApiClient().getMovies(streamData, streamError)
    }

    fun getStreamData(): LiveData<LocalCollections> = streamData

    fun onDestroy(owner: LifecycleOwner) {
        streamData.removeObservers(owner)
        streamError.removeObservers(owner)
    }
}