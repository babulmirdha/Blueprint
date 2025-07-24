package com.alorferi.wallpaperapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import dev.jahir.blueprint.app.databinding.ItemWallpaperXBinding

class WallpaperAdapter(
    private val wallpapers: List<Wallpaper>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    inner class WallpaperViewHolder(private val binding: ItemWallpaperXBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(wallpaper: Wallpaper) {
            binding.imageView.load(wallpaper.imageUrl) {
                crossfade(true)
            }

            binding.imageView.setOnClickListener {
                onClick(wallpaper.imageUrl)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemWallpaperXBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WallpaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        holder.bind(wallpapers[position])
    }

    override fun getItemCount(): Int = wallpapers.size
}