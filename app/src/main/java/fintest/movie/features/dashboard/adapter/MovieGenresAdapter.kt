package fintest.movie.features.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import fintest.movie.R
import fintest.movie.features.dashboard.data.dto.GenresDto
import fintest.movie.features.dashboard.di.moshi
import fintest.movie.features.dashboard.utils.Constant
import kotlinx.android.synthetic.main.item_list_genre.view.*

class MovieGenresAdapter (
    private val context: Context,
    private val listGenre: List<GenresDto.MovieGenresDto>) : RecyclerView.Adapter<MovieGenresAdapter.Holder>(){

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_genre, parent, false))
    }

    override fun getItemCount(): Int = listGenre.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
            holder.view.btn_genre.text = listGenre[position].name

        holder.view.btn_genre.setOnClickListener {
            val jsonAdapter = moshi.adapter(GenresDto.MovieGenresDto::class.java)
            val bundle = bundleOf(Constant.DATA_GENRE to jsonAdapter.toJson(listGenre[position]))
            holder.view.btn_genre.findNavController().navigate(R.id.fragment_genre_movie, bundle)
        }

    }
}