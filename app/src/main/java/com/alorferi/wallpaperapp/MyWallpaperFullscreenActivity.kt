package com.alorferi.wallpaperapp

import android.annotation.SuppressLint
import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.doublegum.gameboii.databinding.ActivityMyWallpaperFullscreenBinding
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
//            binding.setWallpaperBtn.setOnClickListener {
//                when {
//                    wp.drawableResId != null -> {
//                        val drawable = ResourcesCompat.getDrawable(resources, wp.drawableResId, null)
//                        val bitmap = (drawable as BitmapDrawable).bitmap
//                        setWallpaperFromBitmap(bitmap)
//                    }
//
//                    !wp.imageUrl.isNullOrEmpty() -> {
//                        setWallpaperFromUrl(wp.imageUrl)
//                    }
//                }
//            }

            binding.setWallpaperBtn.setOnClickListener { view ->
                PopupMenu(this, view).apply {
                    menu.add("Home screen")
                    menu.add("Lock screen")
                    menu.add("Both")

                    setOnMenuItemClickListener { item ->
                        when (item.title) {
                            "Home screen" -> setWallpaper(wallpaper, WallpaperManager.FLAG_SYSTEM)
                            "Lock screen" -> setWallpaper(wallpaper, WallpaperManager.FLAG_LOCK)
                            "Both" -> setWallpaper(wallpaper, WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK)
                        }
                        true
                    }

                    show()
                }
            }


        }

    }

    @SuppressLint("MissingPermission")
    private fun setWallpaper(wp: Wallpaper, flag: Int) {
        // Show the progress bar on the main thread
        runOnUiThread {
            binding.progressBar.visibility = View.VISIBLE
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val bitmap = when {
                    wp.drawableResId != null -> {
                        val drawable = ResourcesCompat.getDrawable(resources, wp.drawableResId, null)
                        (drawable as BitmapDrawable).bitmap
                    }
                    !wp.imageUrl.isNullOrEmpty() -> {
                        val loader = ImageLoader(this@MyWallpaperFullscreenActivity)
                        val request = ImageRequest.Builder(this@MyWallpaperFullscreenActivity)
                            .data(wp.imageUrl)
                            .allowHardware(false)
                            .build()
                        val result = loader.execute(request) as SuccessResult
                        result.drawable.toBitmap()
                    }
                    else -> null
                }

                bitmap?.let {
                    val wallpaperManager = WallpaperManager.getInstance(this@MyWallpaperFullscreenActivity)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        wallpaperManager.setBitmap(it, null, true, flag)
                    } else {
                        wallpaperManager.setBitmap(it)
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MyWallpaperFullscreenActivity, "Wallpaper set", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MyWallpaperFullscreenActivity, "Failed: ${e.message}", Toast.LENGTH_LONG).show()
                }
            } finally {
                withContext(Dispatchers.Main) {
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }
}
