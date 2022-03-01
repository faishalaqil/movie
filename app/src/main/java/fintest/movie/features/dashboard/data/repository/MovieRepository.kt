package fintest.movie.features.dashboard.data.repository

import fintest.movie.features.dashboard.data.dto.*
import retrofit2.Response
import retrofit2.http.Body

interface MovieRepository {
    suspend fun getTrending(): Response<TrendingDto>
    suspend fun getGenres(): Response<GenresDto>
    suspend fun getMovie(code : String): Response<MovieDto>
    suspend fun getTrailer(code : String): Response<TrailerDto>
    suspend fun getReview(code : String, page : String): Response<ReviewDto>
    suspend fun getMovieGenre(code : String, page : String): Response<TrendingDto>
}