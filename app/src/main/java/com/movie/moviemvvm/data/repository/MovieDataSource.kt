package com.movie.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.movie.moviemvvm.data.api.FIRST_PAGE
import com.movie.moviemvvm.data.api.TheMovieDBInterface
import com.movie.moviemvvm.data.vo.Movie
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//we're using this class to load data based on page number
class MovieDataSource(private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable) : PageKeyedDataSource<Int , Movie>() {

    private var page = FIRST_PAGE

    var networkState : MutableLiveData<NetworkState> = MutableLiveData()

    //this fun is for loading the initial data so the first page
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    callback.onResult(it.movieList,null,page+1)
                         networkState.postValue(NetworkState.LOADED)
                },
                    {
                     networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource",it.message)
                    })
        )
    }

    //this fun is to load next page and will be called  when user scrolls down
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        networkState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            apiService.getPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.totalPages >= params.key){
                        callback.onResult(it.movieList,params.key+1)
                        networkState.postValue(NetworkState.LOADED)
                    }
                    else{
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        Log.e("MovieDataSource",it.message)
                    })
        )
    }

    //this fun is to load the previous page and will be called when user scrolls up but we don't need to do anything here cz our RV will hold the previous data
    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
    }
}