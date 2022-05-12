package com.example.lifediary.ui.activity

import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.lifediary.Notifications
import com.example.lifediary.R
import com.example.lifediary.databinding.ActivityMainBinding
import com.example.lifediary.di.DiScopes
import com.example.lifediary.utils.IntentConstants.ToDoListItemNotification.ACTION_SHOW_CURRENT_CALENDAR_DAY
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import toothpick.Toothpick
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var navigationHolder: NavigatorHolder
    private val viewModel: MainActivityViewModel by viewModels()
    private val navigator = AppNavigator(this, R.id.mainContainer)

    override fun onCreate(savedInstanceState: Bundle?) {
        bindAppScope()
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ensureToDoListNotificationChannel()
        performAction(intent?.action)
        setupNetworkConnectivityListener()
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

    private fun setupNetworkConnectivityListener() {
        // TODO Refactoring
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.onNetworkConnectivityAvailable()
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                lifecycleScope.launch(Dispatchers.Main) {
                    viewModel.onNetworkConnectivityLost()
                }
            }
        }

        val connectivityManager = getSystemService(ConnectivityManager::class.java) as ConnectivityManager
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigationHolder.removeNavigator()
        super.onPause()
    }

    override fun onDestroy() {
        Toothpick.closeScope(DiScopes.APP_SCOPE)
        super.onDestroy()
    }
}