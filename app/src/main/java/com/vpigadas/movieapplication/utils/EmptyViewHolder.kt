package com.vpigadas.movieapplication.utils

import android.view.View
import com.vpigadas.movieapplication.abstraction.AbstractViewHolder

class EmptyViewHolder<T>(itemView: View) : AbstractViewHolder<T>(itemView) {
    override fun bind(data: T) {}
}