package com.example.moviebox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.moviebox.data.MovieDatabase
import com.example.moviebox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        setupActionBarWithNavController(navController)

        val sharedPreference =  getSharedPreferences("MovieBox", Context.MODE_PRIVATE)
        val moviesAddedBefore = sharedPreference.getBoolean("moviesAddedBefore",false)

        if (!moviesAddedBefore) {
            val sharedPreferenceEditor = sharedPreference.edit()
            sharedPreferenceEditor.putBoolean("moviesAddedBefore", true)
            sharedPreferenceEditor.commit()
        }

        MovieDatabase.initDB(this, moviesAddedBefore)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}