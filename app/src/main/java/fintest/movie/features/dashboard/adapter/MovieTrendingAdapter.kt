package fintest.movie.features.dashboard.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.islamkhsh.CardSliderAdapter
import fintest.movie.R
import fintest.movie.features.dashboard.data.dto.TrendingDto
import fintest.movie.features.dashboard.utils.Constant
import kotlinx.android.synthetic.main.item_list_movie.view.*
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import fintest.movie.features.dashboard.di.moshi

class MovieTrendingAdapter(private val data: List<TrendingDto.TrendingMovieDto>)
    : CardSliderAdapter<MovieTrendingAdapter.CardViewHolder>() {
    override fun bindVH(holder: CardViewHolder, position: Int) {
        val movie = data[position]
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_movie, parent, false)

        return CardViewHolder(view)
    }

    override fun getItemCount() = data.size


    class CardViewHolder(view: View) : RecyclerView.ViewHolder(view)
}