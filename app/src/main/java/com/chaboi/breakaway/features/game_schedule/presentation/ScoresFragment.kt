package com.chaboi.breakaway.features.game_schedule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chaboi.breakaway.databinding.FragmentScoresBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoresFragment : Fragment() {

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
                it.viewModel = scoresViewModel
            }.root
    }
}