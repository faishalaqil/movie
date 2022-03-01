package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReviewDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "page")
    val page: Int?,
    @field:Json(name = "results")
    val results: List<ReviewMovieDto>?,
    @field:Json(name = "total_pages")
    val total_pages: Int?,
    @field:Json(name = "total_results")
    val total_results: Int?
) {

    @JsonClass(generateAdapter = true)
    data class ReviewMovieDto(
        @Json(name = "author")
        val author: String?,
        @Json(name = "author_details")
        val author_details: AuthorDetailDto?,
        @Json(name = "content")
        val content: String?,
        @Json(name = "created_at")
        val created_at: String?,
        @Json(name = "id")
        val id: String?,
        @Json(name = "updated_at")
        val updated_at: String?,
        @Json(name = "url")
        val url: String?
    )

    @JsonClass(generateAdapter = true)
    data class AuthorDetailDto(
        @Json(name = "name")
        val name: String?,
        @Json(name = "username")
        val username: String?,
        @Json(name = "avatar_path")
        val avatar_path: String?,
        @Json(name = "rating")
        val rating: String?
    )
}