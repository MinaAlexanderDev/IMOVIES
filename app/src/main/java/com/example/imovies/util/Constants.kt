package com.example.imovies.util

import com.example.imovies.BuildConfig

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val STARTING_PAGE_INDEX = 0
    const val PAGE_SIZE = 10
    const val IMAGE_BASE_UR = "https://image.tmdb.org/t/p/w500/"
    const val DATABASE_NAME = "movies_database"
    const val TABLE_MOVIES = "movies_table"

    //    const val API_KEY = "d31c90f6e90f1993a5cbce70c8c53ce8"
    val API_KEY = BuildConfig.API_KEY
    const val LANGUAGE = "en"
}