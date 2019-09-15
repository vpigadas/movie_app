package com.vpigadas.movieapplication.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.models.LocalCollectionMovies
import com.vpigadas.movieapplication.models.LocalCollections
import com.vpigadas.movieapplication.utils.AppDatabase
import com.vpigadas.movieapplication.utils.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    private val streamData: MutableLiveData<LocalCollections> = MutableLiveData()
    private val streamError: MutableLiveData<Exception> = MutableLiveData()

    private val databaseInstance = AppDatabase.getInstance(application).getMovieDao()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    init {
        launch(Dispatchers.IO) {
            val favorite = databaseInstance.getAllMovies() ?: listOf()
            ApiClient().getMovies(favorite, streamData, streamError)
        }
    }

    fun getStreamData(): LiveData<LocalCollections> = streamData

    fun refreshFavoriteList() {
        launch(Dispatchers.IO) {
            val favorite = databaseInstance.getAllMovies() ?: listOf()

            streamData.value?.apply {
                this.favorite = LocalCollectionMovies(R.string.collection_favorite, favorite)
                streamData.postValue(this)
            }
        }
    }

    fun onDestroy(owner: LifecycleOwner) {
        streamData.removeObservers(owner)
        streamError.removeObservers(owner)
    }
}