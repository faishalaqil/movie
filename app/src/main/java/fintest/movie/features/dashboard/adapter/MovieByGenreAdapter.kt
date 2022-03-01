package fintest.movie.features.dashboard.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import fintest.movie.R
import fintest.movie.features.dashboard.data.dto.TrendingDto
import fintest.movie.features.dashboard.di.moshi
import fintest.movie.features.dashboard.utils.Constant
import kotlinx.android.synthetic.main.item_list_movie_genre.view.*

class MovieByGenreAdapter(private val context: Context,
                         private val listReview: List<TrendingDto.TrendingMovieDto>)
    : RecyclerView.Adapter<MovieByGenreAdapter.Holder>(){

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie_genre, parent, false))
    }

    override fun getItemCount(): Int = listReview.size

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val movie = listReview[position]
        holder.itemView.movie_rating.text = movie.vote_average.toString()
        holder.itemView.movie_title.text = movie.original_title
        holder.itemView.movie_desc.text = movie.overview
        holder.itemView.run {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(RoundedCorners(25))
            Glide.with(holder.itemView)
                .load(Uri.parse(Constant.IMAGE_URL + movie.poster_path))
                .apply(requestOptions)
                .into(movie_poster)
        }

        holder.itemView.setOnClickListener {
            val jsonAdapter = moshi.adapter(TrendingDto.TrendingMovieDto::class.java)
            val bundle = bundleOf(Constant.DATA_TRENDING to jsonAdapter.toJson(movie))
            holder.itemView.findNavController().navigate(R.id.fragment_movie, bundle)
        }
    }
}