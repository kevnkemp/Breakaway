package com.chaboi.breakaway.features.game_schedule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chaboi.breakaway.databinding.FragmentGameScheduleBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GameScheduleFragment : Fragment() {

    private val gameScheduleViewModel: GameScheduleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
          return FragmentGameScheduleBinding
            .inflate(inflater, container, false)
            .also {
                it.lifecycleOwner = this
                it.viewModel = gameScheduleViewModel
            }.root
    }
}