package com.example.imovies.data.domain

import androidx.paging.PagingData
import com.example.imovies.data.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMoviesPagerUseCase {
    fun loadMoviePage(): Flow<PagingData<Movie>>
}