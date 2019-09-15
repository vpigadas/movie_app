package com.vpigadas.movieapplication.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vpigadas.marvelapp.character.MovieDao
import com.vpigadas.movieapplication.models.LocalMovie

@Database(
    entities = arrayOf(LocalMovie::class),
    version = 4
)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}

object AppDatabase {
    private const val dbName = "movie_app_database"

    fun getInstance(context: Context): MovieDatabase =
        Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            dbName
        ).fallbackToDestructiveMigration().build()
}