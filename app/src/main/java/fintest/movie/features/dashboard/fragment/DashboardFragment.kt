package fintest.movie.features.dashboard.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fintest.movie.R
import fintest.movie.features.dashboard.adapter.MovieGenresAdapter
import fintest.movie.features.dashboard.adapter.MovieTrendingAdapter
import fintest.movie.features.dashboard.data.dto.GenresDto
import fintest.movie.features.dashboard.data.dto.TrendingDto
import fintest.movie.features.dashboard.utils.Status
import fintest.movie.features.dashboard.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.swipe_refresh
import kotlinx.android.synthetic.main.fragment_movie.view.*
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE


class DashboardFragment : Fragment() {

    private val viewModel : MovieViewModel by viewModel()
    lateinit var fragmentView: View
    private var listMovieGenre = mutableListOf<GenresDto.MovieGenresDto>()
    private var listMovie = mutableListOf<TrendingDto.TrendingMovieDto>()
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AlertDialog.Builder(requireContext())
                        .setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton(getString(R.string.common_yes)) { _, _ ->
                            requireActivity().moveTaskToBack(true)
                            requireActivity().finish()
                        }
                        .setNegativeButton(getString(R.string.common_no), null)
                        .show()
            }
        })
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        //setHasOptionsMenu(false)
        requireActivity().window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        fragmentView = view
        // Hide the status bar.
        (activity as AppCompatActivity).supportActionBar?.hide()
        swipeRefresh = view.swipe_refresh

        swipeRefresh.setOnRefreshListener {
            Handler().postDelayed(Runnable {
                swipeRefresh.isRefreshing = false
                viewModel.getGenres()
                viewModel.getTrending()
            }, 2000)
        }

        fragmentView.list_movie.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val topRowVerticalPosition =
                if (fragmentView.list_movie == null || fragmentView.list_movie.childCount == 0) 0
                else fragmentView.list_movie.getChildAt(0).top
            swipeRefresh.isEnabled = topRowVerticalPosition >= 0 && scrollX == oldScrollX
        }

        observerTrending()
        observerGenres()
        viewModel.getGenres()
        viewModel.getTrending()

        return view
    }

    private fun observerTrending() {
        viewModel.trending.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        listMovie.clear()
                        view?.list_movie_indicator?.indicatorsToShow = 7
                        for (i in 0 until 6){
                            it.data?.results?.get(i)?.let { it1 -> listMovie.add(it1) }
                        }

                        view?.list_movie?.adapter = MovieTrendingAdapter(listMovie)
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

    private fun observerGenres() {
        viewModel.genres.observe(viewLifecycleOwner,
            androidx.lifecycle.Observer {
                when(it.status) {
                    Status.SUCCESS -> {
                        listMovieGenre.clear()
                        view?.list_genre?.layoutManager = LinearLayoutManager(this.activity, LinearLayoutManager.HORIZONTAL, false)
                        it.data?.genre?.iterator()?.forEach {
                            listMovieGenre.add(it)
                        }

                        val adapter = MovieGenresAdapter(requireContext(), listMovieGenre)
                        adapter.notifyDataSetChanged()
                        view?.list_genre?.adapter = adapter
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