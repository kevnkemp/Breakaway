package com.chaboi.breakaway.features.scores.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.chaboi.breakaway.R
import com.chaboi.breakaway.databinding.FragmentScoresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment : Fragment(), ScoresActionCallback {

    private val scoresViewModel: ScoresViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
          return FragmentScoresBinding
            .inflate(inflater, container, false)
            .also {
                it.lifecycleOwner = this
                scoresViewModel.callback = this
                it.viewModel = scoresViewModel
            }.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }

    override fun openGameDetails(gamePk: String) = findNavController()
        .navigate(R.id.action_scoresFragment_to_scoreDetailsFragment, bundleOf("gamePk" to gamePk))
}