package com.example.intent

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object DataHelper {

    fun getProducts(context: Context): List<Product> {
        val jsonString: String
        try {
            jsonString = context.assets.open("products.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return emptyList()
        }

        // 1. Baca JSON ke dalam List<ProductJson>
        val listProductType = object : TypeToken<List<ProductJson>>() {}.type
        val productsFromJson: List<ProductJson> = Gson().fromJson(jsonString, listProductType)

        // 2. Ubah (map) setiap ProductJson menjadi Product
        return productsFromJson.map { productJson ->
            // Cari ID drawable berdasarkan nama file (String) dari JSON
            val imageResId = context.resources.getIdentifier(
                productJson.image,
                "drawable",
                context.packageName
            )
            // Buat objek Product yang sebenarnya dengan ID gambar (Int)
            Product(imageResId, productJson.name, productJson.stockStatus)
        }
    }
}