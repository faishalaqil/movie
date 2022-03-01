package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrendingDto(
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "results")
    val results: List<TrendingMovieDto>?,
    @field:Json(name = "total_pages")
    val total_pages: Int?,
    @field:Json(name = "total_results")
    val total_results: Int?
) {

    @JsonClass(generateAdapter = true)
    data class TrendingMovieDto(
        @Json(name = "original_language")
        val original_language: String?,
        @Json(name = "original_title")
        val original_title: String?,
        @Json(name = "overview")
        val overview: String?,
        @Json(name = "release_date")
        val release_date: String?,
        @Json(name = "video")
        val video: Boolean?,
        @Json(name = "title")
        val title: String?,
        @Json(name = "adult")
        val adult: Boolean?,
        @Json(name = "backdrop_path")
        val backdrop_path: String?,
        @Json(name = "genre_ids")
        val genre_ids: List<Int>?,
        @Json(name = "vote_count")
        val vote_count: Int?,
        @Json(name = "vote_average")
        val vote_average: Double?,
        @Json(name = "id")
        val id: Int?,
        @Json(name = "poster_path")
        val poster_path: String?,
        @Json(name = "popularity")
        val popularity: Double?,
        @Json(name = "media_type")
        val media_type: String?,
    )
}