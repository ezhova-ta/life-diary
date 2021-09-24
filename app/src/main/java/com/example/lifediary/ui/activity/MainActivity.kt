package com.example.lifediary.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lifediary.Notifications
import com.example.lifediary.R
import com.example.lifediary.databinding.ActivityMainBinding
import com.example.lifediary.di.DiScopes
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.ACTION_SHOW_CURRENT_CALENDAR_DAY
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var navigationHolder: NavigatorHolder
    private lateinit var viewModel: MainActivityViewModel
    private val navigator = AppNavigator(this, R.id.mainContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        bindAppScope()
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        ensureToDoListNotificationChannel()
        performAction(intent?.action)
    }

    private fun bindAppScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
    }

    private fun ensureToDoListNotificationChannel() {
        Notifications.ensureChannel(applicationContext, Notifications.ToDoListChannel)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        performAction(intent?.action)
    }

    private fun performAction(action: String?) {
        when(action) {
            ACTION_SHOW_CURRENT_CALENDAR_DAY -> viewModel.onShowCurrentCalendarDayActionRequested()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }
}