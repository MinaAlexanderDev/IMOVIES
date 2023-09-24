package com.example.imovies.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.imovies.util.Constants
import com.google.gson.annotations.SerializedName

@Entity(tableName = Constants.TABLE_MOVIES)
data class Movie(
    @SerializedName("adult")
    val adult: Boolean? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("id")
    var id: Int,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    @SerializedName("overview")
    val overview: String? = null,
    @SerializedName("popularity")
    val popularity: Double? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("video")
    val video: Boolean? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null,
    @ColumnInfo(name = "page")
    var page: Int? = null,
    @PrimaryKey(autoGenerate = true)
    var rowId: Int,
)