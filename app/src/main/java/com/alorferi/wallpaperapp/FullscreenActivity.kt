package com.alorferi.wallpaperapp

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import dev.jahir.blueprint.app.databinding.ActivityFullscreenBinding
//import com.alorferi.wallpaperapp.databinding.ActivityFullscreenBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FullscreenActivity : ComponentActivity() {

    private lateinit var binding: ActivityFullscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityFullscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("image_url")

        imageUrl?.let {
            // Show image
            binding.fullscreenImage.load(it)

            // Set wallpaper button
            binding.setWallpaperBtn.setOnClickListener {
                setWallpaper(imageUrl)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setWallpaper(imageUrl: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val loader = ImageLoader(this@FullscreenActivity)
                val request = ImageRequest.Builder(this@FullscreenActivity)
                    .data(imageUrl)
                    .allowHardware(false) // Try changing to true if fails
                    .build()

                val result = loader.execute(request) as SuccessResult
                val bitmap = result.drawable.toBitmap()

                val wallpaperManager = WallpaperManager.getInstance(this@FullscreenActivity)
                wallpaperManager.setBitmap(bitmap)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FullscreenActivity, "Wallpaper set successfully", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@FullscreenActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
