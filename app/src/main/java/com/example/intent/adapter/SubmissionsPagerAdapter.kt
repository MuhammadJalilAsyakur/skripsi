package com.example.intent.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.intent.ActiveSubmissionsFragment
import com.example.intent.CompletedSubmissionsFragment

class SubmissionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2 // Karena kita punya 2 tab

    override fun createFragment(position: Int): Fragment {
        // Mengembalikan fragment yang sesuai berdasarkan posisi tab
        return when (position) {
            0 -> ActiveSubmissionsFragment()
            1 -> CompletedSubmissionsFragment()
            else -> throw IllegalStateException("Invalid position $position")
        }
    }
}