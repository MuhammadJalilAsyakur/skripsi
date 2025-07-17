package com.example.intent

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.intent.databinding.FragmentHomeBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var sessionManager: SessionManager
    private lateinit var productAdapter: ProductAdapter
    private val fullProductList = mutableListOf<Product>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())

        // 1. Lakukan semua setup awal di sini, HANYA SEKALI.
        setupRecyclerView()
        setupSearchView()
        displayUsername()
    }

    override fun onResume() {
        super.onResume()
        // 2. Setiap kali kembali ke fragment, muat ulang data.
        loadInitialProducts()
    }

    private fun setupRecyclerView() {
        // Buat adapter HANYA SEKALI dengan daftar kosong.
        productAdapter = ProductAdapter(mutableListOf())
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = productAdapter
    }

    private fun displayUsername() {
        val userDetails = sessionManager.getUserDetails()
        val userName = userDetails[SessionManager.KEY_NAME]
        binding.tvUserName.text = "$userName !"
    }

    private fun loadInitialProducts() {
        showSkeleton(true)
        Handler(Looper.getMainLooper()).postDelayed({
            if (_binding != null) {
                // Ambil data dari JSON
                val products = DataHelper.getProducts(requireContext())
                // Simpan ke daftar utama
                fullProductList.clear()
                fullProductList.addAll(products)
                // Update adapter dengan data lengkap
                productAdapter.filterList(fullProductList)
                // Sembunyikan skeleton
                showSkeleton(false)
            }
        }, 1500)
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Panggil fungsi filter setiap kali ada teks baru
                filterProducts(newText)
                return true
            }
        })
    }

    private fun filterProducts(query: String?) {
        val filteredList = mutableListOf<Product>()
        if (query.isNullOrEmpty()) {
            // Jika query kosong, tampilkan semua produk dari daftar utama
            filteredList.addAll(fullProductList)
        } else {
            // Jika ada query, filter dari daftar utama
            for (product in fullProductList) {
                if (product.name.lowercase(Locale.ROOT).contains(query.lowercase(Locale.ROOT))) {
                    filteredList.add(product)
                }
            }
        }
        // Kirim hasil filter ke adapter yang SAMA
        productAdapter.filterList(filteredList)
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
        _binding = null
    }
}