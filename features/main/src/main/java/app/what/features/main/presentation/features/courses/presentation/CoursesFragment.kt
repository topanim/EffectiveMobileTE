package app.what.features.main.presentation.features.courses.presentation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import app.what.domain.model.Course
import app.what.features.main.R
import app.what.features.main.databinding.FragmentCoursesBinding
import app.what.features.main.presentation.features.courses.domain.CoursesViewModel
import app.what.features.main.presentation.presentation.courseAdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CoursesFragment : Fragment(R.layout.fragment_courses) {

    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CoursesViewModel by inject()

    private val diffCallback = object : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    private val adapter = AsyncListDifferDelegationAdapter(
        diffCallback,
        courseAdapterDelegate { course -> viewModel.onFavoriteClicked(course) }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCoursesBinding.bind(view)

        binding.rvCourses.adapter = adapter
        binding.rvCourses.layoutManager = LinearLayoutManager(requireContext())

        binding.tvBtnSort.setOnClickListener {
            viewModel.onSortClicked()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.courses.collect { list ->
                Log.d("DEBUG_TAG", "Фрагмент получил список: ${list.size} элементов")
                adapter.items = list
                adapter.notifyDataSetChanged()
                Log.d("DEBUG_TAG", "В адаптере сейчас реально: ${adapter.itemCount} элементов")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}