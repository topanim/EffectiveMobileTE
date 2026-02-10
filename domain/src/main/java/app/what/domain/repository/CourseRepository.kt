package app.what.domain.repository

import app.what.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface CourseRepository {
    fun getCourses(): Flow<List<Course>>
    suspend fun toggleFavorite(course: Course)
    fun getFavoriteCourses(): Flow<List<Course>>
}