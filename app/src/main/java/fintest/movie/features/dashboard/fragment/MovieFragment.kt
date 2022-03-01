package fintest.movie.features.dashboard.fragment

import android.R.attr
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import fintest.movie.R
import fintest.movie.features.dashboard.adapter.MovieTrendingAdapter
import fintest.movie.features.dashboard.data.dto.TrendingDto
import fintest.movie.features.dashboard.di.moshi
import fintest.movie.features.dashboard.utils.Constant
import fintest.movie.features.dashboard.utils.Status
import fintest.movie.features.dashboard.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movie.view.*
import kotlinx.android.synthetic.main.item_list_movie.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import fintest.movie.features.dashboard.adapter.MovieGenresAdapter
import fintest.movie.features.dashboard.adapter.MovieReviewAdapter
import fintest.movie.features.dashboard.data.dto.GenresDto
import fintest.movie.features.dashboard.data.dto.ReviewDto
import android.R.attr.data
import android.os.Handler
import android.widget.Toast
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kotlinx.android.synthetic.main.fragment_genre_movie.view.*
import kotlinx.android.synthetic.main.fragment_movie.view.list_review
import kotlinx.android.synthetic.main.fragment_movie.view.swipe_refresh
import kotlinx.android.synthetic.main.fragment_movie.view.title

class MovieFragment : Fragment() {
    private val viewModel : MovieViewModel by viewModel()
    lateinit var fragmentView: View
    lateinit var dataTrending : TrendingDto.TrendingMovieDto
    private var listReview = mutableListOf<ReviewDto.ReviewMovieDto>()
    lateinit var swipeRefresh: SwipeRefreshLayout
    private var maxList = 0
    var page = 1
    var maxPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        val jsonAdapter = moshi.adapter(TrendingDto.TrendingMovieDto::class.java)
        arguments?.let { it -> dataTrending =
            jsonAdapter.fromJson(it.getString(Constant.DATA_TRENDING))!!
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(fintest.movie.R.layout.fragment_movie, container, false)
        fragmentView = view
        val recyclerView = fragmentView.list_review
        swipeRefresh = view.swipe_refresh

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                listReview.clear()
                maxList = 0
                maxPage = 1
                page = 1
                viewModel.getMovie(dataTrending.id.toString())
                viewModel.getTrailer(dataTrending.id.toString())
                viewModel.getReview(dataTrending.id.toString(), page.toString())
            }, 2000)
        }

        observerMovie()
        observerTrailer()
        observerReview()
        listReview.clear()
        viewModel.getMovie(dataTrending.id.toString())
        viewModel.getTrailer(dataTrending.id.toString())
        viewModel.getReview(dataTrending.id.toString(), page.toString())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    // Load more results here
                        if (page <= maxPage){
                            page++
                            viewModel.getReview(dataTrending.id.toString(), page.toString())
                        }
                }
            }
        })
        return view
    }

    private fun observerMovie() {
        viewModel.movie.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {

                        Glide.with(fragmentView)
                            .load(Uri.parse(Constant.IMAGE_URL + it.data?.backdrop_path))
                            .into(fragmentView.img_background)
                        fragmentView.title.text = it.data?.original_title
                        fragmentView.desc.text = it.data?.overview
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }

    private fun observerTrailer() {
        viewModel.trailer.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        var videoId = ""
                        for (i in it.data?.results?.indices!!){
                            if (it.data.results[i].type == "Trailer") {
                                videoId = it.data?.results[i].key.toString()
                            }
                        }
                        if (videoId == "") videoId = it.data?.results[0].key.toString()

                        val youTubePlayerView: YouTubePlayerView = fragmentView.youtube_player_view
                        lifecycle.addObserver(youTubePlayerView)

                        youTubePlayerView.addYouTubePlayerListener(object :
                            AbstractYouTubePlayerListener() {
                            override fun onReady(youTubePlayer: YouTubePlayer) {

                                youTubePlayer.loadVideo(videoId, 0f)
                            }
                        })
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }

    private fun observerReview() {
        val adapter = MovieReviewAdapter(requireContext(), listReview)
        viewModel.review.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        maxPage = it.data?.total_pages!!
                        maxList += it.data.results?.size!!
                        if (page == 1) {
                            view?.list_review?.layoutManager = LinearLayoutManager(
                                this.activity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            it.data.results?.iterator()?.forEach {
                                listReview.add(it)
                            }

                            adapter.notifyDataSetChanged()
                        view?.list_review?.adapter = adapter
                        }else {
                            it.data.results?.iterator()?.forEach {
                                listReview.add(it)
                            }
                            adapter.notifyItemRangeInserted(maxList, listReview.size)
                        }
                        Log.d("TAG", "observerLogin: sukses" )
                    }
                    Status.LOADING -> {
                        Log.d("TAG", "observerLogin: loading" )
                    }
                    Status.ERROR -> {
                        if (!it.message.isNullOrEmpty()) {
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                        }
                        else Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "observerLogin: error" )
                    }
                    Status.DONE -> {
                        Log.d("TAG", "observerLogin: done" )
                    }
                }
            })
    }
}