package com.example.intent

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.intent.databinding.FragmentActiveSubmissionsBinding

class ActiveSubmissionsFragment : Fragment() {

    private var _binding: FragmentActiveSubmissionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActiveSubmissionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvActiveSubmissions.layoutManager = LinearLayoutManager(requireContext())

        // Buat data dummy lengkap dengan semua status
        val allSubmissions = listOf(
            Submission(R.drawable.tomat, "Tomat", "3 Mei 2025", 124, SubmissionStatus.PENDING, "Menunggu diterima admin dinas dan UPT"),
            Submission(R.drawable.tomat, "Tomat", "3 Mei 2025", 122, SubmissionStatus.REJECTED, "Di Tolak UPT karena stok abis"),
            Submission(R.drawable.tomat, "Jahe", "3 Mei 2025", 112, SubmissionStatus.APPROVED, "Disetujui - Silakan ambil produk di UPT KOTA TANGERANG")
        )

        // Filter daftar untuk HANYA menampilkan yang statusnya PENDING
        val activeList = allSubmissions.filter { it.status == SubmissionStatus.PENDING }

        // Tampilkan daftar yang sudah difilter ke adapter
        binding.rvActiveSubmissions.adapter = SubmissionAdapter(activeList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}