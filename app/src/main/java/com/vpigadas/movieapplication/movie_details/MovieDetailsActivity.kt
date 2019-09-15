package com.vpigadas.movieapplication.movie_details

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.abstraction.AbstractActivity
import kotlinx.android.synthetic.main.activity_movie_details.*
import kotlinx.android.synthetic.main.content_movie_details.*
import java.text.NumberFormat

const val INT_MOVIE = "argument_movie_id"

class MovieDetailsActivity : AbstractActivity() {
    private lateinit var viewModel: MovieDetailsViewModel

    override fun getLayout(): Int = R.layout.activity_movie_details

    override fun initLayout() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel::class.java)
    }

    override fun resumeLayout() {
        intent.getIntExtra(INT_MOVIE, 0).apply {
            viewModel.getMovieDetails(this)
        }

        viewModel.getStreamData().observe(this, Observer { movie ->
            toolbar.title = movie.title

            movie_title.text = movie.title
            movie_head_rating.text = movie.vote_average.toString()
            movie_description.text = movie.overview

            movie_date.text = movie.release_date
            movie_popularity.text = movie.popularity.toString()
            movie_rating.text = "${movie.vote_average} (${movie.vote_count})"

            movie_budget.text = NumberFormat.getNumberInstance().format(movie.budget)
            movie_revenue.text = NumberFormat.getNumberInstance().format(movie.revenue)

            Glide.with(this@MovieDetailsActivity).load(movie.getPosterImage()).into(movie_img)
            Glide.with(this@MovieDetailsActivity).load(movie.getBackDropImage()).into(movie_poster)
        })
    }

    override fun destroyLayout() {
        viewModel.onDestroy(this)
    }
}
