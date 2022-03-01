package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrailerDto(
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "results")
    val results: List<MovieTrailerDto>?
) {

    @JsonClass(generateAdapter = true)
    data class MovieTrailerDto(
        @Json(name = "iso_639_1")
        val iso_639_1: String?,
        @Json(name = "iso_3166_1")
        val iso_3166_1: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "key")
        val key: String?,
        @Json(name = "site")
        val site: String?,
        @Json(name = "size")
        val size: Int?,
        @Json(name = "type")
        val type: String?,
        @Json(name = "official")
        val official: Boolean?,
        @Json(name = "published_at")
        val published_at: String?,
        @Json(name = "id")
        val id: String?,
    )
}