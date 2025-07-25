package com.alorferi.wallpaperapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.doublegum.gameboii.databinding.ItemWallpaperXBinding

class WallpaperAdapter(
    private val wallpapers: List<Wallpaper>,
    private val onClick: (Wallpaper) -> Unit
) : RecyclerView.Adapter<WallpaperAdapter.WallpaperViewHolder>() {

    inner class WallpaperViewHolder(private val binding: ItemWallpaperXBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(wallpaper: Wallpaper) {
            when {
                wallpaper.drawableResId != null -> {
                    binding.imageView.load(wallpaper.drawableResId) {
                        crossfade(true)
                    }

                    binding.imageView.setOnClickListener {
                        onClick(wallpaper) // OR pass drawable id as String
                    }
                }

                !wallpaper.imageUrl.isNullOrEmpty() -> {
                    binding.imageView.load(wallpaper.imageUrl) {
                        crossfade(true)
                    }

                    binding.imageView.setOnClickListener {
                        onClick(wallpaper)
                    }
                }
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