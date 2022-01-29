package com.chaboi.breakaway

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chaboi.breakaway.features.game_schedule.presentation.GameScheduleFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, GameScheduleFragment())
                .commitNow()
        }
    }
}