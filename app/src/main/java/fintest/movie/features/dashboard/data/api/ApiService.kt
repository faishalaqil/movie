package fintest.movie.features.dashboard.data.api

import fintest.movie.features.dashboard.data.dto.*
import fintest.movie.features.dashboard.utils.Constant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("trending/movie/week")
    suspend fun getTrending(@Query("api_key") apiKey:String = Constant.API_KEY): Response<TrendingDto>

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey:String = Constant.API_KEY): Response<GenresDto>

    @GET("movie/{code}")
    suspend fun getMovie(@Path("code") code : String,
                         @Query("api_key") apiKey:String = Constant.API_KEY): Response<MovieDto>

    @GET("movie/{code}/videos")
    suspend fun getTrailer(@Path("code") code : String,
                         @Query("api_key") apiKey:String = Constant.API_KEY): Response<TrailerDto>

    @GET("movie/{code}/reviews")
    suspend fun getReview(@Path("code") code : String,
                          @Query("page") page:String = "1",
                           @Query("api_key") apiKey:String = Constant.API_KEY): Response<ReviewDto>

    @GET("discover/movie")
    suspend fun getMovieGenre(@Query("with_genres") code : String,
                          @Query("page") page:String = "1",
                          @Query("api_key") apiKey:String = Constant.API_KEY): Response<TrendingDto>
}