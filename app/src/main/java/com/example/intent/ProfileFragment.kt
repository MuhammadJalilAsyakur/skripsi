package com.example.intent

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.intent.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext())
        setupButtonListeners()

        // Setup listener untuk SwipeRefreshLayout
        binding.swipeRefreshLayout.setOnRefreshListener {
            // Tampilkan animasi skeleton
            showInfoCardSkeleton(true)

            // Simulasi loading data dengan jeda
            Handler(Looper.getMainLooper()).postDelayed({
                if (_binding != null) {
                    // Muat ulang data
                    loadProfileData()

                    // Hentikan animasi loading spinner dari SwipeRefreshLayout
                    binding.swipeRefreshLayout.isRefreshing = false
                }
            }, 1500)
        }
    }

    override fun onResume() {
        super.onResume()
        // 1. Tampilkan skeleton saat fragment kembali aktif
        showInfoCardSkeleton(true)

        // 2. Simulasi loading data selama 1.5 detik
        android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
            if (_binding != null) {
                loadProfileData()
            }
        }, 1500)
    }

    private fun loadProfileData() {
        // Ambil data terbaru dari session
        val userDetails = sessionManager.getUserDetails()
        val userName = userDetails[SessionManager.KEY_NAME]
        val userEmail = userDetails[SessionManager.KEY_EMAIL]
        val userPhone = userDetails[SessionManager.KEY_PHONE]
        val userAddress = userDetails[SessionManager.KEY_ADDRESS]

        // Tampilkan data ke UI
        binding.tvProfileName.text = userName
        binding.tvProfileEmail.text = userEmail
        binding.tvProfilePhone.text = if (userPhone.isNullOrEmpty()) "-" else userPhone
        binding.tvProfileAddress.text = if (userAddress.isNullOrEmpty()) "-" else userAddress
        // Anda bisa tambahkan untuk NIK jika ada TextView-nya

        // 3. Setelah data dimuat, sembunyikan skeleton dan tampilkan kartu data asli
        showInfoCardSkeleton(false)

    }


    private fun setupButtonListeners() {
        binding.btnCardEdit.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
            sessionManager.logoutUser()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }

        binding.fabEditPicture.setOnClickListener {
            Toast.makeText(requireContext(), "Fitur ganti foto belum tersedia", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showInfoCardSkeleton(show: Boolean) {
        if (show) {
            binding.shimmerProfileCard.startShimmer()
            binding.shimmerProfileCard.visibility = View.VISIBLE
            binding.cardUserDetails.visibility = View.GONE
        } else {
            binding.shimmerProfileCard.stopShimmer()
            binding.shimmerProfileCard.visibility = View.GONE
            binding.cardUserDetails.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}