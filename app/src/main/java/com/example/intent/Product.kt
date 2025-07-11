package com.example.intent // Sesuaikan package kamu

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val image: Int, // Kita pakai Int untuk ID gambar dari drawable
    val name: String,
    val stockStatus: String
) : Parcelable