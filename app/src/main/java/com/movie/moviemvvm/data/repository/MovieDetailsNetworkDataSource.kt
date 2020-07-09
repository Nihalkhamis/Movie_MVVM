package com.movie.moviemvvm.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.movie.moviemvvm.data.api.TheMovieDBInterface
import com.movie.moviemvvm.data.vo.MovieDetails
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

//here we'll cal api by using RXJava and will return MovieDetails and we'll assign MovieDetails to LiveData

//CompositeDisposable is RXJava component that we'll use when we wants to dispose our api calls so when we want to dispose
//a RXJava thread we can use composite disposable
class MovieDetailsNetworkDataSource (private val apiService : TheMovieDBInterface, private val compositeDisposable: CompositeDisposable){

    //_ means it's private
    private val _networkState = MutableLiveData<NetworkState>()
    val networkState : LiveData<NetworkState>  //we'll have access only to networkState LiveData cz it's public
      get() = _networkState   //this get will be called every time we access this networkState variable so we don't need getter and setter

    private val _downloadedMovieDetailsResponse = MutableLiveData<MovieDetails>()
    val downloadedMovieDetailsResponse : LiveData<MovieDetails>
    get() = _downloadedMovieDetailsResponse

    fun fetchMovieDetails(MovieID : Int){
        _networkState.postValue(NetworkState.LOADING)

        //in try and catch we'll use rx javaThread to make network calls we want
        try {
            compositeDisposable.add(
                apiService.getMovieDetails(MovieID)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            _downloadedMovieDetailsResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                            Log.e("MovieDetailsDataSource", it.message)
                        }
                    )
            )

        }
        catch (e : Exception){
            Log.e("MovieDetailsDataSource", e.message)

        }
    }


}