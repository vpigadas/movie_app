package com.vpigadas.movieapplication.movie_details

import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_movie, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menu?.findItem(R.id.menu_main_setting)?.apply {
            when (viewModel.isFavoriteMovie()) {
                true -> this.setIcon(R.drawable.ic_favorite_white_24dp)
                false -> this.setIcon(R.drawable.ic_favorite_border_white_24dp)
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean = when (item?.itemId) {
        R.id.menu_main_setting -> {
            viewModel.handleFavoriteAction()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    override fun resumeLayout() {
        intent.getIntExtra(INT_MOVIE, 0).apply {
            viewModel.getMovieDetails(this)
        }

        viewModel.getStreamFavorite().observe(this, Observer {
            val message = when (viewModel.isFavoriteMovie()) {
                true -> getString(R.string.favorite_added)
                false -> getString(R.string.favorite_deleted)
            }
            Snackbar.make(toolbar, message, Snackbar.LENGTH_SHORT).show()

            invalidateOptionsMenu()
        })

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
