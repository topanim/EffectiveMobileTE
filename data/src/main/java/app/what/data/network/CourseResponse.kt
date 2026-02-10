package app.what.data.network

import kotlinx.serialization.Serializable

@Serializable
data class CourseResponse(
    val courses: List<CourseDto>
)

@Serializable
data class CourseDto(
    val id: Int,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val startDate: String,
    val hasLike: Boolean,
    val publishDate: String
)