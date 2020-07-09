package com.movie.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import com.movie.moviemvvm.data.api.TheMovieDBInterface
import com.movie.moviemvvm.data.repository.MovieDetailsNetworkDataSource
import com.movie.moviemvvm.data.repository.NetworkState
import com.movie.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable


//this MovieDetailsRepository when we want to cash the data
class MovieDetailsRepository(private val apiService : TheMovieDBInterface) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchSingleMovieDetails(compositeDisposable: CompositeDisposable, movieID : Int) : LiveData<MovieDetails>{
        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService,compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieID)

        return movieDetailsNetworkDataSource.downloadedMovieDetailsResponse
    }

    fun getMovieDetailsNetworkState() : LiveData<NetworkState>{
        return movieDetailsNetworkDataSource.networkState
    }
}