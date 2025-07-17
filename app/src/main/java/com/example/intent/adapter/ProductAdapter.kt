package com.example.intent

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intent.databinding.ItemProductBinding

class ProductAdapter(private var productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

       // ... class ProductViewHolder, onCreateViewHolder, getItemCount (tidak berubah) ...
    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        holder.binding.ivProductImage.setImageResource(product.image)
        holder.binding.tvProductName.text = product.name
        holder.binding.tvStockStatus.text = product.stockStatus

        // TAMBAHKAN BLOK KODE INI
        holder.binding.btnAjukan.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, SubmissionActivity::class.java).apply {
                // Kirim seluruh objek produk ke activity berikutnya
                putExtra("EXTRA_PRODUCT", product)
            }
            context.startActivity(intent)
        }
    }
    // TAMBAHKAN FUNGSI BARU INI
    // 2. UBAH LOGIKA FUNGSI INI
    fun filterList(filteredList: List<Product>) {
        // Ganti daftar yang lama dengan yang baru
        this.productList = filteredList
        // Beri tahu RecyclerView untuk menggambar ulang dirinya
        notifyDataSetChanged()
    }
}