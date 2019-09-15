package com.vpigadas.movieapplication.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vpigadas.movieapplication.R

interface LocalModel {
    fun equalContent(other: LocalModel?): Boolean
}

data class LocalCollections(
    var favorite: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_favorite, listOf()),
    var popular: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_popular, listOf()),
    var coming: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_upcoming, listOf()),
    var latest: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_top_rated, listOf()),
    var playNow: LocalCollectionMovies = LocalCollectionMovies(R.string.collection_now_playing, listOf())
) {
    fun getCollection(): List<LocalCollectionMovies> = when (favorite.movies.isNotEmpty()) {
        true -> listOf(coming, favorite, playNow, popular, latest)
        else -> listOf(coming, playNow, popular, latest)
    }
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

@Entity(tableName = "movies")
data class LocalMovie(
    @PrimaryKey @ColumnInfo(name = "movie_id") val id: Int,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "vote_average") val vote_average: Double,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "release_date") val release_date: String
) : LocalModel {

    constructor(response: MovieResponse) : this(
        response.id,
        "https://image.tmdb.org/t/p/w500/${response.poster_path}",
        response.title,
        response.vote_average,
        response.overview,
        response.release_date
    )

    constructor(response: MovieDetailsResponse) : this(
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