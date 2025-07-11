package com.example.intent

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.intent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Kembalikan ke setup manual
        setupCustomNavBar()
        // Set item pertama sebagai item aktif
        updateNavSelection(R.id.nav_home)
    }

    private fun setupCustomNavBar() {
        binding.navHome.setOnClickListener {
            updateNavSelection(it.id)
            if (navController.currentDestination?.id != R.id.homeFragment) {
                navController.navigate(R.id.homeFragment)
            }
        }
        binding.navCart.setOnClickListener {
            updateNavSelection(it.id)
            if (navController.currentDestination?.id != R.id.cartFragment) {
                navController.navigate(R.id.cartFragment)
            }
        }
        binding.navProfile.setOnClickListener {
            updateNavSelection(it.id)
            if (navController.currentDestination?.id != R.id.profileFragment) {
                navController.navigate(R.id.profileFragment)
            }
        }
    }

    private fun updateNavSelection(selectedId: Int) {
        // 1. Reset semua item ke status tidak aktif (abu-abu, regular)
        binding.navHomeBg.setBackgroundResource(R.drawable.bg_nav_icon_unselected)
        binding.navHomeIcon.setColorFilter(ContextCompat.getColor(this, R.color.text_grey))

        binding.navCartBg.setBackgroundResource(R.drawable.bg_nav_icon_unselected)
        binding.navCartIcon.setColorFilter(ContextCompat.getColor(this, R.color.text_grey))

        binding.navProfileBg.setBackgroundResource(R.drawable.bg_nav_icon_unselected)
        binding.navProfileIcon.setColorFilter(ContextCompat.getColor(this, R.color.text_grey))


        // 2. Atur item yang dipilih menjadi aktif (background hijau, ikon putih, teks hijau)
        when (selectedId) {
            R.id.nav_home -> {
                binding.navHomeBg.setBackgroundResource(R.drawable.bg_nav_icon_selected)
                // UBAH WARNA IKON MENJADI PUTIH
                binding.navHomeIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
            }
            R.id.nav_cart -> {
                binding.navCartBg.setBackgroundResource(R.drawable.bg_nav_icon_selected)
                // UBAH WARNA IKON MENJADI PUTIH
                binding.navCartIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
            }
            R.id.nav_profile -> {
                binding.navProfileBg.setBackgroundResource(R.drawable.bg_nav_icon_selected)
                // UBAH WARNA IKON MENJADI PUTIH
                binding.navProfileIcon.setColorFilter(ContextCompat.getColor(this, android.R.color.white))
            }
        }
    }}