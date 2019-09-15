package com.vpigadas.movieapplication.models

data class MovieApiResponse(
    val results: List<MovieResponse>,
    val page: Int,
    val total_results: Int,
    val total_pages: Int
)

data class MovieResponse(
    val id: Int,
    val popularity: Number,
    val vote_count: Int,
    val poster_path: String,
    val adult: Boolean,
    val backdrop_path: String,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String
)

data class MovieDetailsResponse(
    val id: Int,
    val popularity: Number,
    val vote_count: Int,
    val poster_path: String,
    val adult: Boolean,
    val backdrop_path: String,
    val title: String,
    val vote_average: Double,
    val overview: String,
    val release_date: String,
    val status: String,
    val tagline: String,
    val runtime: String,
    val revenue: Int,
    val imdb_id: String,
    val homepage: String,
    val genres: List<GenresResponse>,
    val budget: Int,
    val production_companies: List<ProductionCompaniesResponse>,
    val production_countries: List<ProductionCountriesResponse>
) {
    fun getPosterImage(): String = "https://image.tmdb.org/t/p/w500/$poster_path"
    fun getBackDropImage(): String = "https://image.tmdb.org/t/p/w500/$backdrop_path"

    fun convertToLocalMovie():LocalMovie{
        return LocalMovie(this)
    }
}

data class GenresResponse(
    val id: Int,
    val name: String
)

data class ProductionCompaniesResponse(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)

data class ProductionCountriesResponse(
    val iso_3166_1: String,
    val name: String
)