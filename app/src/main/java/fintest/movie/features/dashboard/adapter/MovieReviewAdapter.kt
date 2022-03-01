package fintest.movie.features.dashboard.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import fintest.movie.R
import fintest.movie.features.dashboard.data.dto.ReviewDto
import fintest.movie.features.dashboard.utils.Constant
import kotlinx.android.synthetic.main.item_list_review.view.*

class MovieReviewAdapter(private val context: Context,
                         private val listReview: List<ReviewDto.ReviewMovieDto>) : RecyclerView.Adapter<MovieReviewAdapter.Holder>(){

    class Holder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_list_review, parent, false))
    }

    override fun getItemCount(): Int = listReview.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.review_name.text = listReview[position].author
        holder.view.review_review.text = listReview[position].content
        holder.itemView.run {
            try {
                var requestOptions = RequestOptions()
                requestOptions = requestOptions.transforms(RoundedCorners(25))
                val url =listReview[position].author_details?.avatar_path?.replace("/https","https")
                Glide.with(holder.itemView)
                    .load(Uri.parse(url))
                    .apply(requestOptions)
                    .into(review_image)
            }catch (e: Exception){

            }
        }

    }
}