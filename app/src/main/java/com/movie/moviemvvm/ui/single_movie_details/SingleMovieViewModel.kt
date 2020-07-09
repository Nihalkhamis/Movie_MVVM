package com.movie.moviemvvm.ui.single_movie_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.movie.moviemvvm.data.repository.NetworkState
import com.movie.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable

class SingleMovieViewModel(private val movieRepository : MovieDetailsRepository, private val movieID : Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Lazy here is for when we want the movieDetails we'll get it not when this viewModel is initialized this is better performance
    val movieDetails : LiveData<MovieDetails> by lazy {
      movieRepository.fetchSingleMovieDetails(compositeDisposable,movieID)
    }

    val networkState : LiveData<NetworkState> by lazy {
      movieRepository.getMovieDetailsNetworkState()
    }

    //this fun is for when the activity or fragment get destroyed we dispose our composite disposable so there won't be any memory leaks
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}