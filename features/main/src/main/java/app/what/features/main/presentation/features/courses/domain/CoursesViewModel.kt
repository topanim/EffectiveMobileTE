package app.what.features.main.presentation.features.courses.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.what.domain.model.Course
import app.what.domain.usecase.GetCoursesUseCase
import app.what.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CoursesViewModel(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    private var isDescending = false

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            getCoursesUseCase().collect { list ->
                _courses.value = list
            }
        }
    }

    fun onSortClicked() {
        isDescending = !isDescending
        _courses.value = applySort(_courses.value)
    }

    private fun applySort(list: List<Course>): List<Course> {
        return if (isDescending) {
            list.sortedByDescending { it.publishDate }
        } else {
            list.sortedBy { it.publishDate }
        }
    }

    fun onFavoriteClicked(course: Course) {
        viewModelScope.launch {
            toggleFavoriteUseCase(course)
        }
    }
}