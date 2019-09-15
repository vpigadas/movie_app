package com.vpigadas.movieapplication.home

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.abstraction.AbstractActivity
import com.vpigadas.movieapplication.home.collection.CollectionAdapter
import com.vpigadas.movieapplication.listeners.ItemViewClickListener
import com.vpigadas.movieapplication.models.LocalModel
import com.vpigadas.movieapplication.models.LocalMovie
import com.vpigadas.movieapplication.movie_details.INT_MOVIE
import com.vpigadas.movieapplication.movie_details.MovieDetailsActivity
import com.vpigadas.movieapplication.utils.ActivityRouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AbstractActivity() {
    private lateinit var viewModel: HomeViewModel

    override fun getLayout(): Int = R.layout.activity_main

    override fun initLayout() {
        toolbar.title = getString(R.string.app_name)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        recycler.adapter =
            CollectionAdapter(object : ItemViewClickListener {
                override fun onItemClick(model: LocalModel) {
                    when (model) {
                        is LocalMovie -> ActivityRouter(this@MainActivity)
                            .addBundle(INT_MOVIE, model.id)
                            .startIntent(
                                MovieDetailsActivity::class.java
                            )
                    }
                }
            })
    }

    override fun resumeLayout() {
        viewModel.getStreamData().observe(this, Observer {
            (recycler.adapter as? CollectionAdapter)?.submitList(it.getCollection())
        })
    }

    override fun destroyLayout() {
        viewModel.onDestroy(this)
    }
}
