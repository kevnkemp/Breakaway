package com.chaboi.breakaway.features.scores.presentation

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.chaboi.breakaway.core.viewmodel.assistedViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ScoreDetailsFragment : Fragment() {

    private val navArgs: ScoreDetailsFragmentArgs by navArgs()

    @Inject lateinit var detailsViewModelFactory: ScoresDetailsViewModelFactory

    private val viewModel by assistedViewModel {
        detailsViewModelFactory.create(navArgs.gamePk)
    }
}