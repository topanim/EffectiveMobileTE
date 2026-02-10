package app.what.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import app.what.data.local.CourseEntity
import app.what.data.network.CourseDto
import app.what.domain.model.Course
import java.time.LocalDate

class CourseMapper {
    fun mapDtoToDomain(dto: CourseDto, isFavorite: Boolean): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            text = dto.text,
            price = dto.price.replace(" ", "").toInt(),
            rate = dto.rate.toFloat(),
            startDate = parseDate(dto.startDate),
            hasLike = isFavorite,
            publishDate = parseDate(dto.publishDate)
        )
    }

    fun mapEntityToDomain(entity: CourseEntity): Course {
        return Course(
            id = entity.id,
            title = entity.title,
            text = entity.text,
            price = entity.price,
            rate = entity.rate,
            startDate = entity.startDate,
            hasLike = true,
            publishDate = entity.publishDate
        )
    }

    fun mapDomainToEntity(course: Course): CourseEntity {
        return CourseEntity(
            id = course.id,
            title = course.title,
            text = course.text,
            price = course.price,
            rate = course.rate,
            startDate = course.startDate,
            hasLike = true,
            publishDate = course.publishDate
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun parseDate(dateStr: String): LocalDate {
        return LocalDate.parse(dateStr)
    }
}