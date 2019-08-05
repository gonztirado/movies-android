package com.gonztirado.app.features.movies.view

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.gonztirado.app.R
import com.gonztirado.app.core.navigation.Navigator
import com.gonztirado.app.features.movies.viewmodel.MovieView
import com.gonztirado.app.util.extension.inflate
import com.gonztirado.app.util.extension.loadFromUrl
import kotlinx.android.synthetic.main.row_movie.view.*
import javax.inject.Inject
import kotlin.properties.Delegates

class MoviesAdapter
@Inject constructor() : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    internal var collection: List<MovieView> by Delegates.observable(emptyList()) {
        _, _, _ -> notifyDataSetChanged()
    }

    internal var clickListener: (MovieView, Navigator.Extras) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.row_movie))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
            viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movieView: MovieView, clickListener: (MovieView, Navigator.Extras) -> Unit) {
            itemView.movieCardTitle.text = movieView.title
            itemView.moviePoster.loadFromUrl(movieView.poster, R.drawable.movie_empty)
            itemView.setOnClickListener { clickListener(movieView, Navigator.Extras(itemView.moviePoster)) }
        }
    }
}
