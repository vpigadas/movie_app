package com.vpigadas.movieapplication.home.movies

import android.view.View
import com.bumptech.glide.Glide
import com.vpigadas.movieapplication.abstraction.AbstractViewHolder
import com.vpigadas.movieapplication.listeners.ItemViewClickListener
import com.vpigadas.movieapplication.models.LocalModel
import com.vpigadas.movieapplication.models.LocalMovie
import kotlinx.android.synthetic.main.holder_movie.view.*

class MovieViewHolder(itemView: View, listener: ItemViewClickListener) : AbstractViewHolder<LocalModel>(itemView) {
    var localModel: LocalModel? = null

    init {
        itemView.setOnClickListener { localModel?.apply { listener.onItemClick(this) } }
    }

    override fun bind(data: LocalModel) {
        when (data) {
            is LocalMovie -> {
                localModel = data
                Glide.with(itemView.context).load(data.image).into(itemView.movie_image)

                itemView.movie_title.text = data.title
            }
        }
    }
}