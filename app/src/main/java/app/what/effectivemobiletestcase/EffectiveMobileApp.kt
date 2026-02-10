package app.what.effectivemobiletestcase

import android.app.Application
import androidx.room.Room
import app.what.data.local.AppDatabase
import app.what.data.mapper.CourseMapper
import app.what.data.network.CourseApiService
import app.what.data.repository.CourseRepositoryImpl
import app.what.domain.repository.CourseRepository
import app.what.domain.usecase.GetCoursesUseCase
import app.what.domain.usecase.GetFavoriteCoursesUseCase
import app.what.domain.usecase.ToggleFavoriteUseCase
import app.what.features.auth.domain.LoginViewModel
import app.what.features.main.presentation.features.courses.domain.CoursesViewModel
import app.what.features.main.presentation.features.favorites.domain.FavoritesViewModel
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class EffectiveMobileApp : Application() {
    override fun onCreate() {
        super.onCreate()


        startKoin {
            androidContext(this@EffectiveMobileApp)
            modules(appModule, networkModule, databaseModule, repositoryModule)
        }
    }
}

val appModule = module {
    singleOf(::GetCoursesUseCase)
    singleOf(::GetFavoriteCoursesUseCase)
    singleOf(::ToggleFavoriteUseCase)
    viewModelOf(::LoginViewModel)
    singleOf(::FavoritesViewModel)
    singleOf(::CoursesViewModel)
}


val networkModule = module {
    single {
        val json = Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        }
        val contentType = "application/json".toMediaType()

        Retrofit.Builder()
            .baseUrl("https://drive.google.com/")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
    }

    single<CourseApiService> {
        get<Retrofit>().create(CourseApiService::class.java)
    }
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "courses_db"
        ).fallbackToDestructiveMigration(true).build()
    }

    single { get<AppDatabase>().courseDao() }
}

val repositoryModule = module {
    single { CourseMapper() }

    single<CourseRepository> {
        CourseRepositoryImpl(
            apiService = get(),
            courseDao = get(),
            mapper = get()
        )
    }
}
