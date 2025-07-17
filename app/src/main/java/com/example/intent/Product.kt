package com.example.intent

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    @DrawableRes val image: Int,
    val name: String,
    val stockStatus: String
) : Parcelable