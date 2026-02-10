package app.what.features.main.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import app.what.features.main.R
import app.what.features.main.databinding.ActivityMainBinding
import app.what.features.main.presentation.features.courses.presentation.CoursesFragment
import app.what.features.main.presentation.features.favorites.presentation.FavoritesFragment
import app.what.features.main.presentation.features.profile.presentation.ProfileFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()

        if (savedInstanceState == null) {
            replaceFragment(CoursesFragment())
        }
    }

    private fun setupNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_courses -> {
                    replaceFragment(CoursesFragment())
                    true
                }

                R.id.nav_favorites -> {
                    replaceFragment(FavoritesFragment())
                    true
                }

                R.id.nav_account -> {
                    replaceFragment(ProfileFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, fragment)
            .commit()
    }
}