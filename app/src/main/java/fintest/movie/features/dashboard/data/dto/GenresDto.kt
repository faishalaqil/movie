package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenresDto(
    @field:Json(name = "genres")
        val genre: List<MovieGenresDto>
) {

    @JsonClass(generateAdapter = true)
    data class MovieGenresDto(
            @Json(name = "id")
            val id: Int,
            @Json(name = "name")
            val name: String,
    )
}