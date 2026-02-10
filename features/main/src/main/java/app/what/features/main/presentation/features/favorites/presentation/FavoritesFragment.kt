package app.what.features.main.presentation.features.favorites.presentation

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import app.what.features.main.R
import app.what.features.main.databinding.FragmentFavoritesBinding
import app.what.features.main.presentation.features.favorites.domain.FavoritesViewModel
import app.what.features.main.presentation.presentation.courseAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject


class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private val viewModel: FavoritesViewModel by inject()
    private val adapter = ListDelegationAdapter(
        courseAdapterDelegate { course -> viewModel.onFavoriteClicked(course) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentFavoritesBinding.bind(view)

        binding.rvFavorites.adapter = adapter
        binding.rvFavorites.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.favoriteCourses.collect { list ->
                adapter.items = list
                adapter.notifyDataSetChanged()

                binding.tvEmptyState.isVisible = list.isEmpty()
            }
        }
    }
}