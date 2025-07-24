package com.alorferi.wallpaperapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import dev.jahir.blueprint.app.databinding.FragmentMyWallpapersBinding
import dev.jahir.frames.ui.fragments.WallpapersFragment

class MyWallpapersFragment : WallpapersFragment() {

    private var _binding: FragmentMyWallpapersBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WallpaperViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyWallpapersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.wallpapers.observe(viewLifecycleOwner) { wallpapers ->
            val adapter = WallpaperAdapter(wallpapers) { wallpaper ->
                val intent = Intent(requireContext(), MyWallpaperFullscreenActivity::class.java)
                intent.putExtra(Wallpaper::class.simpleName, wallpaper)
                startActivity(intent)
            }

            binding.wallpaperRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
            binding.wallpaperRecyclerView.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
