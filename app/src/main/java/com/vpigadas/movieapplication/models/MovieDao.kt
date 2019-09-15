package com.vpigadas.marvelapp.character

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.vpigadas.movieapplication.models.LocalMovie

@Dao
interface MovieDao {

    @Query("Select * from movies")
    fun getAllMovies(): List<LocalMovie>

    @Query("Select * from movies where movie_id = :movieId limit 1")
    fun getMovie(movieId: Int): LocalMovie

    @Insert
    fun insertMovie(movie: LocalMovie)

    @Delete
    fun deleteMovie(movie: LocalMovie)
}