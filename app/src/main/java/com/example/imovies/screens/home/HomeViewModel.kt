package com.example.imovies.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.imovies.data.domain.IMoviesPagerUseCase
import com.example.imovies.data.model.Movie
import com.example.imovies.di.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: IMoviesPagerUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _moviesList = MutableStateFlow(PagingData.empty<Movie>())
    val moviesList: StateFlow<PagingData<Movie>> = _moviesList

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch(ioDispatcher) {
            useCase.loadMoviePage().cachedIn(this).collect { _moviesList.value = it }

        }
    }
}