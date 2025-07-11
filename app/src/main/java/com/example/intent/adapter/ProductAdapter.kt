package com.example.intent

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.intent.databinding.ItemProductBinding

class ProductAdapter(private val productList: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    // 1. ViewHolder sekarang memegang 'binding' bukan 'itemView'
    class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        // 2. Inflate layout menggunakan View Binding
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]

        // 3. Akses view melalui 'holder.binding'
        holder.binding.ivProductImage.setImageResource(product.image)
        holder.binding.tvProductName.text = product.name
        holder.binding.tvStockStatus.text = product.stockStatus

        // 4. Tambahkan logika untuk tombol "Ajukan" di sini
        holder.binding.btnAjukan.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, SubmissionActivity::class.java).apply {
                // Kirim seluruh objek produk ke activity berikutnya
                putExtra("EXTRA_PRODUCT", product)
            }
            context.startActivity(intent)
        }
    }
}