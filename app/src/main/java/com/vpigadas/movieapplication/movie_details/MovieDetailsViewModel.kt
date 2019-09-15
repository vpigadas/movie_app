package com.vpigadas.movieapplication.movie_details

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vpigadas.movieapplication.models.MovieDetailsResponse
import com.vpigadas.movieapplication.utils.network.ApiClient

class MovieDetailsViewModel(application: Application) : AndroidViewModel(application) {
    private val streamData: MutableLiveData<MovieDetailsResponse> = MutableLiveData()
    private val streamError: MutableLiveData<Exception> = MutableLiveData()

    fun getMovieDetails(movieId: Int) {
        ApiClient().getMovieDetails(movieId, streamData, streamError)
    }

    fun getStreamData(): LiveData<MovieDetailsResponse> = streamData

    fun onDestroy(owner: LifecycleOwner) {
        streamData.removeObservers(owner)
        streamError.removeObservers(owner)
    }
}