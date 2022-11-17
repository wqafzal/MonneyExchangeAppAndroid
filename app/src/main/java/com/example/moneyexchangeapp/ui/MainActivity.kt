package com.example.moneyexchangeapp.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

    }
}