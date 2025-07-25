package com.alorferi.wallpaperapp

import com.doublegum.gameboii.R


class WallpaperRepository {

//    fun getWallpapers(): List<Wallpaper> {
//        return listOf(
//            Wallpaper("https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"),
//            Wallpaper("https://images.unsplash.com/photo-1526045612212-70caf35c14df"),
//            Wallpaper("https://images.unsplash.com/photo-1495567720989-cebdbdd97913"),
//            Wallpaper("https://images.unsplash.com/photo-1507525428034-b723cf961d3e"),
//            Wallpaper("https://images.unsplash.com/photo-1470770903676-69b98201ea1c"),
//            Wallpaper("https://images.unsplash.com/photo-1506748686214-e9df14d4d9d0"),
//            Wallpaper("https://images.unsplash.com/photo-1526045612212-70caf35c14df"),
//            Wallpaper("https://images.unsplash.com/photo-1495567720989-cebdbdd97913"),
//            Wallpaper("https://images.unsplash.com/photo-1507525428034-b723cf961d3e"),
//            Wallpaper("https://images.unsplash.com/photo-1470770903676-69b98201ea1c"),
//        )
//    }



    fun getWallpapers(): List<Wallpaper> {
        return listOf(
            Wallpaper(drawableResId = R.drawable.wallpaper_1),
            Wallpaper(drawableResId = R.drawable.wallpaper_2),
            Wallpaper(drawableResId = R.drawable.wallpaper_3),
            Wallpaper(imageUrl = "https://images.unsplash.com/photo-1495567720989-cebdbdd97913"),
            Wallpaper(imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e")
        )
    }

}
