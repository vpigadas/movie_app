package com.vpigadas.movieapplication.home.collection

import android.view.View
import com.vpigadas.movieapplication.abstraction.AbstractViewHolder
import com.vpigadas.movieapplication.home.movies.MoviesAdapter
import com.vpigadas.movieapplication.listeners.ItemViewClickListener
import com.vpigadas.movieapplication.models.LocalCollectionMovies
import com.vpigadas.movieapplication.models.LocalModel
import kotlinx.android.synthetic.main.holder_horizontal_recycler.view.*

class CollectionViewHolder(itemView: View, private val listener: ItemViewClickListener) :
    AbstractViewHolder<LocalModel>(itemView) {

    override fun bind(data: LocalModel) {
        when (data) {
            is LocalCollectionMovies -> {
                itemView.holder_horizontal_recycler_title.text = itemView.context.getString(data.title)
                itemView.holder_horizontal_recycler.adapter = MoviesAdapter(listener).apply { submitList(data.movies) }
            }
        }
    }
}