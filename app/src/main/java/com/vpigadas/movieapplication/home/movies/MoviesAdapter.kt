package com.vpigadas.movieapplication.home.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.abstraction.AbstractAdapter
import com.vpigadas.movieapplication.abstraction.AbstractViewHolder
import com.vpigadas.movieapplication.listeners.ItemViewClickListener
import com.vpigadas.movieapplication.models.LocalModel
import com.vpigadas.movieapplication.models.LocalMore
import com.vpigadas.movieapplication.models.LocalMovie

class MoviesAdapter(private val listener: ItemViewClickListener) : AbstractAdapter<LocalModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<LocalModel> =
        when (viewType) {
            R.layout.holder_movie -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                MovieViewHolder(view, listener)
            }
            R.layout.holder_more -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                MoreViewHolder(view)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is LocalMovie -> R.layout.holder_movie
        is LocalMore -> R.layout.holder_more
        else -> 0
    }
}