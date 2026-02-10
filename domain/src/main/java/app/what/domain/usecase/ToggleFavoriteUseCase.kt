package app.what.domain.usecase

import app.what.domain.model.Course
import app.what.domain.repository.CourseRepository

class ToggleFavoriteUseCase(private val repository: CourseRepository) {
    suspend operator fun invoke(course: Course) {
        repository.toggleFavorite(course)
    }
}