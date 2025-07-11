package com.example.intent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.intent.databinding.FragmentHomeBinding // <-- GANTI KE FRAGMENTHOMEBINDING

class HomeFragment : Fragment() {

    // Gunakan pola ini untuk view binding di Fragment agar aman dari memory leak
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout untuk fragment ini
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Semua logika yang berhubungan dengan view diletakkan di sini
        sessionManager = SessionManager(requireContext()) // <-- Gunakan requireContext()
        // Pindahkan setup RecyclerView ke sini agar tidak dipanggil berulang kali
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        // Ambil nama user dari session dan tampilkan
        val userDetails = sessionManager.getUserDetails()
        val userName = userDetails[SessionManager.KEY_NAME]
        binding.tvUserName.text = "$userName !" // Pastikan ID ini ada di fragment_home.xml

        setupProductData()


    }

    // Di dalam kelas HomeFragment
    override fun onResume() {
        super.onResume()
        // Tampilkan skeleton
        showSkeleton(true)
        // Simulasi loading data
        Handler(Looper.getMainLooper()).postDelayed({
            // TAMBAHKAN PENGECEKAN INI
            if (_binding != null) {
                setupProductData()
            }
        }, 1500)
    }
    private fun setupProductData() {
        // Ini data dummy, nanti bisa diganti dari API
        val productList = listOf(
            Product(R.drawable.tomat, "Tomat", "Stok ada"),
            Product(R.drawable.ic_launcher_foreground, "Lidah buaya", "Stok ada"),
            Product(R.drawable.ic_launcher_foreground, "Jahe", "Stok ada"),
            Product(R.drawable.ic_launcher_foreground, "Sirih", "Stok ada"),
        )


        val adapter = ProductAdapter(productList)
        binding.rvProducts.adapter = adapter

        // Setelah data siap, sembunyikan skeleton dan tampilkan RecyclerView
        showSkeleton(false)
    }


    private fun showSkeleton(show: Boolean) {
        if (show) {
            binding.shimmerLayoutHome.startShimmer()
            binding.shimmerLayoutHome.visibility = View.VISIBLE
            binding.rvProducts.visibility = View.GONE
        } else {
            binding.shimmerLayoutHome.stopShimmer()
            binding.shimmerLayoutHome.visibility = View.GONE
            binding.rvProducts.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Wajib ada untuk membersihkan binding saat fragment dihancurkan
        _binding = null
    }


}