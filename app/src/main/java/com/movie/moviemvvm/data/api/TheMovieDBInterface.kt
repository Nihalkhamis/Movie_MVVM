package com.movie.moviemvvm.data.api

import com.movie.moviemvvm.data.vo.MovieDetails
import com.movie.moviemvvm.data.vo.MovieResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDBInterface {
    //https://api.themoviedb.org/3/movie/popular?api_key=6a5da20458dbbce788b7c3a1a4958a81&page=1
    //https://api.themoviedb.org/3/movie/419704?api_key=6a5da20458dbbce788b7c3a1a4958a81
    //https://api.themoviedb.org/3/

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page : Int) : Single<MovieResponse>

    //the api_key will pass it via interceptor to put it inside the link this is a better practice than put it directly inside the link
    @GET("movie/{movie_id}")
    //Single is a type of observable in RXJava that emits only one value or an error
    fun getMovieDetails(@Path("movie_id") id : Int) : Single<MovieDetails>
}