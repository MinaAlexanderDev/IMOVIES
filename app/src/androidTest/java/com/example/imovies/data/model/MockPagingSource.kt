package com.example.imovies.data.model

import androidx.paging.PagingSource
import androidx.paging.PagingState

class MockPagingSource(private val movieList: List<Movie>) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = movieList, prevKey = null, nextKey = 1
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return 1
    }
}