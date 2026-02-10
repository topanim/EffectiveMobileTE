package app.what.domain.usecase

import app.what.domain.model.Course
import app.what.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteCoursesUseCase(private val repository: CourseRepository) {
    operator fun invoke(): Flow<List<Course>> {
        return repository.getFavoriteCourses()
    }
}