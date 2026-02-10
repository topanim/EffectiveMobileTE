package app.what.features.main.presentation.features.profile.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import app.what.features.main.R
import app.what.features.main.databinding.FragmentProfileBinding


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentProfileBinding.bind(view)
    }
}