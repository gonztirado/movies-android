package com.gonztirado.app.features.movies.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gonztirado.app.R
import com.gonztirado.app.features.movies.viewmodel.MovieRatingView
import com.gonztirado.app.util.extension.inflate
import kotlinx.android.synthetic.main.row_movie_rating.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MovieRatingsAdapter
@Inject constructor() : RecyclerView.Adapter<MovieRatingsAdapter.ViewHolder>() {

    internal var collection: List<MovieRatingView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_movie_rating))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position])

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieRatingView: MovieRatingView) {
            itemView.movieRatingSource.text = movieRatingView.source
            itemView.movieRatingValue.text = movieRatingView.value
        }
    }
}
