package com.movie.moviemvvm.data.repository

//enums are data type that hold set of constants
enum class Status{
    RUNNING,
    SUCCESS,
    FAILED
}

//kotlin doesn't need to have constructor separetly
class NetworkState(val status : Status, val msg : String) {

    //companion object when we want something to be static so we can call these 3 variables without creating an instance of NetworkState class
    companion object{

        //val is used for non mutable variables so we cannot change it but var is used for mutable ones
        val LOADED : NetworkState
        val LOADING : NetworkState
        val ERROR : NetworkState
        var ENDOFLIST : NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS,"Success")
            LOADING = NetworkState(Status.RUNNING,"Running")
            ERROR = NetworkState(Status.FAILED,"Something went wrong")
            ENDOFLIST = NetworkState(Status.FAILED,"You have reached the end")
        }

    }
}