package com.example.imovies

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.imovies.data.model.Movie

class MockPagingSource(private val movieList: List<Movie>) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = movieList, prevKey = null, nextKey = 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return 1
    }
}