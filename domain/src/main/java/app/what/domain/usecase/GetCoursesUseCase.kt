package app.what.domain.usecase

import app.what.domain.model.Course
import app.what.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetCoursesUseCase(private val repository: CourseRepository) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getCourses()
    }

    fun sort(courses: List<Course>): List<Course> {
        return courses.sortedByDescending { it.publishDate }
    }
}