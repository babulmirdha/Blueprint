package com.alorferi.wallpaperapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import dev.jahir.blueprint.app.databinding.ActivityMainXBinding


class MainActivity : ComponentActivity()  {
    private lateinit var binding: ActivityMainXBinding
    private val viewModel: WallpaperViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainXBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.wallpapers.observe(this) { wallpapers ->
            val adapter = WallpaperAdapter(wallpapers) { imageUrl ->
                val intent = Intent(this, FullscreenActivity::class.java)
                intent.putExtra("image_url", imageUrl)
                startActivity(intent)
            }

            binding.wallpaperRecyclerView.layoutManager = GridLayoutManager(this, 2)
            binding.wallpaperRecyclerView.adapter = adapter
        }
    }
}