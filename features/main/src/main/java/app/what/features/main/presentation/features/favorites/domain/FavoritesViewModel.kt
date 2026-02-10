package app.what.features.main.presentation.features.favorites.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.what.domain.model.Course
import app.what.domain.usecase.GetFavoriteCoursesUseCase
import app.what.domain.usecase.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class FavoritesViewModel(
    private val getFavoriteCoursesUseCase: GetFavoriteCoursesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val favoriteCourses: StateFlow<List<Course>> = getFavoriteCoursesUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun onFavoriteClicked(course: Course) {
        viewModelScope.launch {
            toggleFavoriteUseCase(course)
        }
    }
}