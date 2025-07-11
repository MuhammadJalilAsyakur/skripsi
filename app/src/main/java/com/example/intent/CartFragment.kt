package com.example.intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.intent.adapter.SubmissionsPagerAdapter
import com.example.intent.databinding.FragmentCartBinding
import com.google.android.material.tabs.TabLayoutMediator

class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi adapter dan viewpager
        val pagerAdapter = SubmissionsPagerAdapter(this)
        binding.viewPager.adapter = pagerAdapter

        // Hubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            // Atur nama untuk setiap tab
            when (position) {
                0 -> tab.text = "Pengajuan Aktif"
                1 -> tab.text = "Selesai"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}