package com.example.imovies.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.imovies.data.model.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies_table ")
    fun moviesPagingSource(): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<Movie>)

    @Query("DELETE FROM movies_table")
    suspend fun clearAllMovies()

    @Query("DELETE FROM sqlite_sequence")
    fun clearPrimaryKey()
}
