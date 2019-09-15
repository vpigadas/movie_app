package com.vpigadas.movieapplication.home.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import com.vpigadas.movieapplication.R
import com.vpigadas.movieapplication.abstraction.AbstractAdapter
import com.vpigadas.movieapplication.abstraction.AbstractViewHolder
import com.vpigadas.movieapplication.listeners.ItemViewClickListener
import com.vpigadas.movieapplication.models.LocalModel

class CollectionAdapter(private val listener: ItemViewClickListener) : AbstractAdapter<LocalModel>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder<LocalModel> =
        when (viewType) {
            R.layout.holder_horizontal_recycler -> {
                val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
                CollectionViewHolder(view, listener)
            }
            else -> super.onCreateViewHolder(parent, viewType)
        }

    override fun getItemViewType(position: Int): Int {
        return R.layout.holder_horizontal_recycler
    }
}