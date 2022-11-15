package com.example.moneyexchangeapp.ui

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.moneyexchangeapp.R
import com.example.moneyexchangeapp.base.BaseActivity
import com.google.android.material.navigation.NavigationView
import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = findNavController(R.id.nav_host_fragment)

    }
}