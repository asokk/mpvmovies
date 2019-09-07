package com.machioni.libermovies.data.remote.datasource

import com.machioni.libermovies.BuildConfig
import com.machioni.libermovies.data.remote.model.DetailedMovieRM
import com.machioni.libermovies.data.remote.model.MovieSearchRM
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesRemoteDataSource {
    @GET("/")
    fun getMovies(@Query("s") searchParam: String,
                  @Query("page") page: Long = 1,
                  @Query("apikey") apiKey : String = BuildConfig.OMDB_API_KEY): Single<MovieSearchRM>

    @GET("/")
    fun getMovieDetails(@Query("i") imdbId: String,
                        @Query("apikey") apiKey : String = BuildConfig.OMDB_API_KEY): Single<DetailedMovieRM>
}