package fintest.movie.features.dashboard.data.repository.impl

import fintest.movie.features.dashboard.data.api.ApiService
import fintest.movie.features.dashboard.data.dto.*
import fintest.movie.features.dashboard.data.repository.MovieRepository
import org.koin.core.KoinComponent
import retrofit2.Response

class MovieRepositoryImpl(
    private val apiService: ApiService //constructor injection
) : MovieRepository, KoinComponent {

    override suspend fun getGenres(): Response<GenresDto> {
        return apiService.getGenres()
    }

    override suspend fun getTrending(): Response<TrendingDto> {
        return apiService.getTrending()
    }

    override suspend fun getMovie(code : String): Response<MovieDto> {
        return apiService.getMovie(code)
    }

    override suspend fun getTrailer(code : String): Response<TrailerDto> {
        return apiService.getTrailer(code)
    }

    override suspend fun getReview(code : String, page : String): Response<ReviewDto> {
        return apiService.getReview(code, page)
    }

    override suspend fun getMovieGenre(code : String, page : String): Response<TrendingDto> {
        return apiService.getMovieGenre(code, page)
    }
}