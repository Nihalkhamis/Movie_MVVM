package com.movie.moviemvvm.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.movie.moviemvvm.data.api.TheMovieDBInterface
import com.movie.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

//DataSource object loads pages for a single pagedList. The factory class creates new instances of PagedList in response to content updates
class MovieDataSourceFactory (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : DataSource.Factory<Int , Movie>() {

    val moviesLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, Movie> {

        val movieDataSource = MovieDataSource(apiService,compositeDisposable)

        moviesLiveDataSource.postValue(movieDataSource)
        return movieDataSource

    }

}