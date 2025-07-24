package com.alorferi.wallpaperapp

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import dev.jahir.blueprint.app.databinding.ActivityMyWallpaperFullscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MyWallpaperFullscreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityMyWallpaperFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityMyWallpaperFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val wallpaper = intent.getSerializableExtra(Wallpaper::class.simpleName) as? Wallpaper

        wallpaper?.let { wp ->

            // Load image
            when {
                wp.drawableResId != null -> binding.fullscreenImage.load(wp.drawableResId)
                !wp.imageUrl.isNullOrEmpty() -> binding.fullscreenImage.load(wp.imageUrl)
            }

            // Set wallpaper button
            binding.setWallpaperBtn.setOnClickListener {
                when {
                    wp.drawableResId != null -> {
                        val drawable = ResourcesCompat.getDrawable(resources, wp.drawableResId, null)
                        val bitmap = (drawable as BitmapDrawable).bitmap
                        setWallpaperFromBitmap(bitmap)
                    }

                    !wp.imageUrl.isNullOrEmpty() -> {
                        setWallpaperFromUrl(wp.imageUrl)
                    }
                }
            }
        }

    }

    private fun setWallpaperFromBitmap(bitmap: Bitmap) {
        val wallpaperManager = WallpaperManager.getInstance(this)
        wallpaperManager.setBitmap(bitmap)
    }


    @SuppressLint("MissingPermission")
    private fun setWallpaperFromUrl(imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loader = ImageLoader(this@MyWallpaperFullscreenActivity)
                val request = ImageRequest.Builder(this@MyWallpaperFullscreenActivity)
                    .data(imageUrl)
                    .allowHardware(false) // Try changing to true if fails
                    .build()

                val result = loader.execute(request) as SuccessResult
                val bitmap = result.drawable.toBitmap()

                val wallpaperManager = WallpaperManager.getInstance(this@MyWallpaperFullscreenActivity)
                wallpaperManager.setBitmap(bitmap)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MyWallpaperFullscreenActivity, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MyWallpaperFullscreenActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
