package app.what.data.repository

import android.util.Log
import app.what.data.local.CourseDao
import app.what.data.mapper.CourseMapper
import app.what.data.network.CourseApiService
import app.what.domain.model.Course
import app.what.domain.repository.CourseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CourseRepositoryImpl(
    private val apiService: CourseApiService,
    private val courseDao: CourseDao,
    private val mapper: CourseMapper
) : CourseRepository {

    override fun getCourses(): Flow<List<Course>> = flow {
        val response = apiService.getCourses()
        val remoteCourses = response.courses

        emitAll(
            courseDao.getFavoriteIdsFlow().map { favoriteIds ->
                remoteCourses.map { dto ->
                    val isLiked = favoriteIds.contains(dto.id)
                    mapper.mapDtoToDomain(dto, isLiked)
                }
            }
        )
    }

    override suspend fun toggleFavorite(course: Course) {
        if (!course.hasLike) {
            val entity = mapper.mapDomainToEntity(course)
            courseDao.insertFavorite(entity)
        } else {
            courseDao.deleteFavorite(course.id)
        }
    }

    override fun getFavoriteCourses(): Flow<List<Course>> {
        return courseDao.getFavoriteCourses().map { entities ->
            entities.map { mapper.mapEntityToDomain(it) }
        }
    }
}