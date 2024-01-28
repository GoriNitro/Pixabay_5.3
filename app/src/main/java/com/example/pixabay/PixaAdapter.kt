package com.example.pixabay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.example.pixabay.databinding.ItemImageBinding
import java.util.ArrayList

class PixaViewHolder(private var binding: ItemImageBinding) : ViewHolder(binding.root) {
    fun onBind(image: ImageModel) {
        with(binding) {
            tvLike.text = image.likes.toString()
            imgPixabay.load(image.largeImageURL)
        }
    }
}

class PixaAdapter(var list: ArrayList<ImageModel>) : Adapter<PixaViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PixaViewHolder {
        return PixaViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PixaViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}
