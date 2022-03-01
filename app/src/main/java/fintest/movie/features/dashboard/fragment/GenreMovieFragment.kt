package fintest.movie.features.dashboard.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fintest.movie.R
import fintest.movie.features.dashboard.adapter.MovieByGenreAdapter
import fintest.movie.features.dashboard.adapter.MovieReviewAdapter
import fintest.movie.features.dashboard.data.dto.GenresDto
import fintest.movie.features.dashboard.data.dto.ReviewDto
import fintest.movie.features.dashboard.data.dto.TrendingDto
import fintest.movie.features.dashboard.di.moshi
import fintest.movie.features.dashboard.utils.Constant
import fintest.movie.features.dashboard.utils.Status
import fintest.movie.features.dashboard.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_genre_movie.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class GenreMovieFragment : Fragment() {
    private val viewModel : MovieViewModel by viewModel()
    lateinit var fragmentView: View
    lateinit var dataGenre : GenresDto.MovieGenresDto
    private var listMovie = mutableListOf<TrendingDto.TrendingMovieDto>()
    lateinit var swipeRefresh: SwipeRefreshLayout
    private var maxList = 0
    var page = 1
    var maxPage = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        val jsonAdapter = moshi.adapter(GenresDto.MovieGenresDto::class.java)
        arguments?.let { it -> dataGenre =
            jsonAdapter.fromJson(it.getString(Constant.DATA_GENRE))!!
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_genre_movie, container, false)
        fragmentView = view
        val recyclerView = fragmentView.list_movie
        fragmentView.genre.text = dataGenre.name
        swipeRefresh = view.swipe_refresh

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                listMovie.clear()
                maxList = 0
                maxPage = 1
                page = 1
                viewModel.getMovieGenre(dataGenre.id.toString(), page.toString())
            }, 2000)
        }

        observerMovieGenre()
        listMovie.clear()
        viewModel.getMovieGenre(dataGenre.id.toString(), page.toString())

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!recyclerView.canScrollVertically(1) && dy != 0) {
                    // Load more results here
                    if (page <= maxPage){
                        page++
                        viewModel.getMovieGenre(dataGenre.id.toString(), page.toString())
                    }
                }
            }
        })

        return view
    }

    private fun observerMovieGenre() {
        val adapter = MovieByGenreAdapter(requireContext(), listMovie)
        viewModel.movieGenre.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        maxPage = it.data?.total_pages!!
                        maxList += it.data.results?.size!!
                        if (page == 1) {
                            view?.list_movie?.layoutManager = LinearLayoutManager(
                                this.activity,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            it.data.results?.iterator()?.forEach {
                                listMovie.add(it)
                            }
                            adapter.notifyDataSetChanged()
                            view?.list_movie?.adapter = adapter
                        }else {
                            it.data.results?.iterator()?.forEach {
                                listMovie.add(it)
                            }
                            adapter.notifyItemRangeInserted(maxList, listMovie.size)
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