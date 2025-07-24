package com.alorferi.wallpaperapp

import java.io.Serializable


data class Wallpaper(
    val imageUrl: String? = null,
    val drawableResId: Int? = null
): Serializable

