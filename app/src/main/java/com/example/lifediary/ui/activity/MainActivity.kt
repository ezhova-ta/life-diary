package com.example.lifediary.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lifediary.R
import com.example.lifediary.databinding.ActivityMainBinding
import com.example.lifediary.di.DiScopes
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.androidx.AppNavigator
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

        // TODO Find another solution to instantiate the MainActivityViewModel
        viewModel.onActivityCreated()
    }

    private fun bindAppScope() {
        val appScope = Toothpick.openScope(DiScopes.APP_SCOPE)
        Toothpick.inject(this, appScope)
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