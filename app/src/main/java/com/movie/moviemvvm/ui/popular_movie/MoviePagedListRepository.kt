package com.movie.moviemvvm.ui.popular_movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.movie.moviemvvm.data.api.POST_PER_PAGE
import com.movie.moviemvvm.data.api.TheMovieDBInterface
import com.movie.moviemvvm.data.repository.MovieDataSource
import com.movie.moviemvvm.data.repository.MovieDataSourceFactory
import com.movie.moviemvvm.data.repository.NetworkState
import com.movie.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository(private val apiService : TheMovieDBInterface) {

    lateinit var moviePagedList : LiveData<PagedList<Movie>>
    lateinit var movieDataSourceFactory: MovieDataSourceFactory

    fun fetchMoviesPagedList(compositeDisposable: CompositeDisposable) : LiveData<PagedList<Movie>>{
        movieDataSourceFactory = MovieDataSourceFactory(apiService,compositeDisposable)

        val config : PagedList.Config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        moviePagedList = LivePagedListBuilder(movieDataSourceFactory,config).build()
        return moviePagedList
    }

    fun getNetworkState() : LiveData<NetworkState>{
       return Transformations.switchMap<MovieDataSource, NetworkState>(
           //here we accessing network state mutableLiveData and transforming it to liveData from moviesLiveDataSource liveData
           movieDataSourceFactory.moviesLiveDataSource,MovieDataSource::networkState
       )
    }

}