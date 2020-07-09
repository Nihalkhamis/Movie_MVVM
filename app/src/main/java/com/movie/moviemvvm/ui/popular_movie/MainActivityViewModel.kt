package com.movie.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.movie.moviemvvm.data.repository.NetworkState
import com.movie.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MainActivityViewModel(private val movieRepository : MoviePagedListRepository) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<Movie>> by lazy{
        movieRepository.fetchMoviesPagedList(compositeDisposable)
    }

    val networkState : LiveData<NetworkState> by lazy {
        movieRepository.getNetworkState()
    }

    fun listIsEmpty() : Boolean{
        return moviePagedList.value ?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}