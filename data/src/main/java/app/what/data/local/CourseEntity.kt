package app.what.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "favorite_courses")
data class CourseEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val text: String,
    val price: Int,
    val rate: Float,
    val startDate: LocalDate,
    val hasLike: Boolean,
    val publishDate: LocalDate
)