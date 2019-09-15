package com.vpigadas.movieapplication.utils.network

import androidx.lifecycle.MutableLiveData
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.gson.responseObject
import com.github.kittinunf.fuel.httpGet
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.models.*

class ApiClient() {
    private val apiKey: String = "394982741155b4eed8aeed3d9782bc2a"

    init {
        FuelManager.instance.apply {
            baseParams = listOf(
                Pair("api_key", apiKey)
            )
            basePath = "https://api.themoviedb.org/3"
        }
    }

    fun getMovies(success: MutableLiveData<LocalCollections>, failed: MutableLiveData<Exception>) {
        getUpComingMovies(LocalCollections(), success, failed)
    }

    fun getUpComingMovies(
        stored: LocalCollections,
        success: MutableLiveData<LocalCollections>,
        failed: MutableLiveData<Exception>
    ) {
        "/movie/upcoming".httpGet().responseObject<MovieApiResponse> { request, response, result ->
            result.fold(success = { apiResponse ->

                stored.coming =
                    LocalCollectionMovies(R.string.collection_upcoming, apiResponse.results.map { LocalMovie(it) })
                success.postValue(stored)

                getPopularMovies(stored, success, failed)
            }
                , failure = { failed.postValue(it) })
        }
    }

    fun getPopularMovies(
        stored: LocalCollections,
        success: MutableLiveData<LocalCollections>,
        failed: MutableLiveData<Exception>
    ) {
        "/movie/popular".httpGet().responseObject<MovieApiResponse> { request, response, result ->
            result.fold(success = { apiResponse ->
                stored.popular =
                    LocalCollectionMovies(R.string.collection_popular, apiResponse.results.map { LocalMovie(it) })
                success.postValue(stored)

                getNowPlayingMovies(stored, success, failed)
            }
                , failure = { failed.postValue(it) })
        }
    }

    fun getNowPlayingMovies(
        stored: LocalCollections,
        success: MutableLiveData<LocalCollections>,
        failed: MutableLiveData<Exception>
    ) {
        "/movie/now_playing".httpGet().responseObject<MovieApiResponse> { request, response, result ->
            result.fold(success = { apiResponse ->
                stored.playNow =
                    LocalCollectionMovies(R.string.collection_now_playing, apiResponse.results.map { LocalMovie(it) })
                success.postValue(stored)

                getTopRatedMovies(stored, success, failed)
            }
                , failure = { failed.postValue(it) })
        }
    }

    fun getTopRatedMovies(
        stored: LocalCollections,
        success: MutableLiveData<LocalCollections>,
        failed: MutableLiveData<Exception>
    ) {
        "/movie/top_rated".httpGet().responseObject<MovieApiResponse> { request, response, result ->
            result.fold(success = { apiResponse ->
                stored.latest =
                    LocalCollectionMovies(R.string.collection_top_rated, apiResponse.results.map { LocalMovie(it) })
                success.postValue(stored)
            }
                , failure = { failed.postValue(it) })
        }
    }

    fun getMovieDetails(
        movieId: Int,
        success: MutableLiveData<MovieDetailsResponse>,
        failed: MutableLiveData<Exception>
    ) {
        "/movie/$movieId".httpGet().responseObject<MovieDetailsResponse> { request, response, result ->
            result.fold(success = { success.postValue(it) }
                , failure = { failed.postValue(it) })
        }
    }
}
