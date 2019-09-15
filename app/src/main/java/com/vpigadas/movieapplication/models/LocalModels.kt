package com.vpigadas.movieapplication.models

import com.vpigadas.movieapplication.R

interface LocalModel {
    fun equalContent(other: LocalModel?): Boolean
}

data class LocalCollections(
    var popular: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_popular, listOf()),
    var coming: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_upcoming, listOf()),
    var latest: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_top_rated, listOf()),
    var playNow: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_now_playing, listOf())
) {
    fun getCollection(): List<LocalCollectionMovies> = listOf(coming, playNow, popular, latest)
}

data class LocalCollectionMovies(
    val title: Int,
    val movies: List<LocalModel>
) : LocalModel {
    override fun equalContent(other: LocalModel?): Boolean {
        when (other) {
            is LocalCollectionMovies -> {
                if (title == other.title) return true
            }
        }
        return false
    }
}

data class LocalMovie(
    val id: Int,
    val image: String,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
) : LocalModel {

    constructor(response: MovieResponse) : this(
        response.id,
        "https://image.tmdb.org/t/p/w500/${response.poster_path}",
        response.title,
        response.vote_average,
        response.overview,
        response.release_date
    )

    override fun equalContent(other: LocalModel?): Boolean {
        when (other) {
            is LocalMovie -> {
                if (id == other.id) return true
            }
        }
        return false
    }
}

data class LocalMore(val name: String = "More") : LocalModel {
    override fun equalContent(other: LocalModel?): Boolean = true
}