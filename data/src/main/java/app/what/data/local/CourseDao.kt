package app.what.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Query("SELECT * FROM favorite_courses")
    fun getFavoriteCourses(): Flow<List<CourseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(course: CourseEntity)

    @Query("DELETE FROM favorite_courses WHERE id = :courseId")
    suspend fun deleteFavorite(courseId: Int)

    @Query("SELECT id FROM favorite_courses")
    suspend fun getFavoriteIds(): List<Int>

    @Query("SELECT id FROM favorite_courses")
    fun getFavoriteIdsFlow(): Flow<List<Int>>
}