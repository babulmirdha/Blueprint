package com.alorferi.wallpaperapp


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WallpaperViewModel : ViewModel() {
    private val repository = WallpaperRepository()
    private val _wallpapers = MutableLiveData<List<Wallpaper>>()
    val wallpapers: LiveData<List<Wallpaper>> = _wallpapers

    init {
        _wallpapers.value = repository.getWallpapers()
    }
}