package com.example.imovies.screens.movie_details.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imovies.data.model.MovieDetails
import com.example.imovies.data.repository.IMovieDetailsRepository
import com.example.imovies.di.IoDispatcher
import com.example.imovies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val repository: IMovieDetailsRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>>(Resource.Loading())
    val movieDetails: StateFlow<Resource<MovieDetails>> = _movieDetails

    suspend fun getMovieDetails(movieId: Int) {
        viewModelScope.launch(ioDispatcher) {
            val movie = repository.getMovieDetails(movieId)
            _movieDetails.value = movie
        }

    }
}

