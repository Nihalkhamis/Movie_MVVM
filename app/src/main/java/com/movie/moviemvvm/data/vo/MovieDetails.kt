package com.movie.moviemvvm.data.vo


import com.google.gson.annotations.SerializedName

//the purpose of data class is to hold data, in java we write setters and getters but we don't have to do it here zc we're using data class
data class MovieDetails(
    val budget: Int,
    val id: Int,
    val overview: String,
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val rating: Double
)