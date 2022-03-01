package fintest.movie.features.dashboard.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

//    {"status_code":7,
//    "status_message":"Invalid API key: You must be granted a valid key.",
//    "success":false}

@JsonClass(generateAdapter = true)
data class ResponseDto(
    @field:Json(name = "status_code")
    val status_code: Int?,
    @field:Json(name = "status_message")
    val status_message: String?,
    @field:Json(name = "success")
    val success: Boolean?
)