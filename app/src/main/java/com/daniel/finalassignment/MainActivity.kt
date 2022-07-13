package com.daniel.finalassignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.daniel.finalassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Daniel Cruz Rodriguez
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    /**
     *Creates a new value "binding" that allows you to more easily write code that interacts with views
     */
    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Set the activity content from a layout resource. The resource will be inflated, adding all top-level views to the activity.
         */
        setContentView(binding.root)

        /**
         * 
         */
        val navController = findNavController(R.id.nav_container_fragment)
        binding.navigationBar.setupWithNavController(navController)
    }
}