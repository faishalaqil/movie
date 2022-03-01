package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieDto(
    @field:Json(name = "adult")
    val adult: Boolean?,
    @field:Json(name = "backdrop_path")
    val backdrop_path: String?,
    @field:Json(name = "belongs_to_collection")
    val belongs_to_collection: BelongsToCollectionDto?,
    @field:Json(name = "budget")
    val budget: Int?,
    @field:Json(name = "genres")
    val genres: List<GenresDto.MovieGenresDto>?,
    @field:Json(name = "homepage")
    val homepage: String?,
    @field:Json(name = "id")
    val id: Int?,
    @field:Json(name = "imdb_id")
    val imdb_id: String?,
    @field:Json(name = "original_language")
    val original_language: String?,
    @field:Json(name = "original_title")
    val original_title: String?,
    @field:Json(name = "overview")
    val overview: String?,
    @field:Json(name = "popularity")
    val popularity: Double?,
    @field:Json(name = "poster_path")
    val poster_path: String?,
    @field:Json(name = "production_companies")
    val production_companies: List<ProductionCompaniesDto>?,
    @field:Json(name = "production_countries")
    val production_countries: List<ProductionCountriesDto>?,
    @field:Json(name = "release_date")
    val release_date: String?,
    @field:Json(name = "revenue")
    val revenue: Int?,
    @field:Json(name = "runtime")
    val runtime: Int?,
    @field:Json(name = "spoken_languages")
    val spoken_languages: List<SpokenLanguageDto>?,
    @field:Json(name = "status")
    val status: String?,
    @field:Json(name = "tagline")
    val tagline: String?,
    @field:Json(name = "title")
    val title: String?,
    @field:Json(name = "video")
    val video: Boolean?,
    @field:Json(name = "vote_average")
    val vote_average: Double?,
    @field:Json(name = "vote_count")
    val vote_count: Int?
) {

    @JsonClass(generateAdapter = true)
    data class BelongsToCollectionDto(
        @Json(name = "id")
        val id: Int?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "poster_path")
        val poster_path: String?,
        @Json(name = "backdrop_path")
        val backdrop_path: String?
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCompaniesDto(
        @Json(name = "id")
        val id: Int?,
        @Json(name = "logo_path")
        val logo_path: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "origin_country")
        val origin_country: String?
    )

    @JsonClass(generateAdapter = true)
    data class ProductionCountriesDto(
        @Json(name = "iso_3166_1")
        val iso_3166_1: String?,
        @Json(name = "name")
        val name: String?
    )

    @JsonClass(generateAdapter = true)
    data class SpokenLanguageDto(
        @Json(name = "english_name")
        val english_name: String?,
        @Json(name = "name")
        val name: String?,
        @Json(name = "iso_639_1")
        val iso_639_1: String?
    )
}