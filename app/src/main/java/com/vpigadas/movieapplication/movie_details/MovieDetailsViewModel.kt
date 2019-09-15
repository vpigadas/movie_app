package com.vpigadas.movieapplication.movie_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.movieapplication.models.MovieDetailsResponse
import com.vpigadas.movieapplication.utils.AppDatabase
import com.vpigadas.movieapplication.utils.network.ApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    private val streamData: MutableLiveData<MovieDetailsResponse> = MutableLiveData()
    private val streamFavorite: MutableLiveData<Unit> = MutableLiveData()
    private val streamError: MutableLiveData<Exception> = MutableLiveData()

    private val databaseInstance = AppDatabase.getInstance(application).getMovieDao()
    private var isFavorite: Boolean = false

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    fun getMovieDetails(movieId: Int) {
        ApiClient().getMovieDetails(movieId, streamData, streamError)

        launch(Dispatchers.IO) {
            isFavorite = when (databaseInstance.getMovie(movieId)) {
                null -> false
                else -> true
            }
            streamFavorite.postValue(Unit)
        }
    }

    fun getStreamData(): LiveData<MovieDetailsResponse> = streamData
    fun getStreamFavorite(): LiveData<Unit> = streamFavorite

    fun isFavoriteMovie(): Boolean = isFavorite

    fun handleFavoriteAction() {
        val movie = streamData.value?.convertToLocalMovie() ?: return

        launch(Dispatchers.IO) {
            when (isFavoriteMovie()) {
                false -> databaseInstance.insertMovie(movie)
                true -> databaseInstance.deleteMovie(movie)
            }
            isFavorite = !isFavorite
            streamFavorite.postValue(Unit)
        }
    }

    fun onDestroy(owner: LifecycleOwner) {
        streamData.removeObservers(owner)
        streamFavorite.removeObservers(owner)
        streamError.removeObservers(owner)
    }
}