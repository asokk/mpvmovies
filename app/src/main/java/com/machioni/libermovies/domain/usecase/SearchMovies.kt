package com.machioni.libermovies.domain.usecase

import com.machioni.libermovies.common.di.BackgroundScheduler
import com.machioni.libermovies.common.di.MainScheduler
import com.machioni.libermovies.domain.model.Movie
import com.machioni.libermovies.domain.model.Page
import com.machioni.libermovies.domain.repositoryinterface.MoviesRepositoryInterface
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import javax.inject.Inject

class SearchMovies @Inject constructor(@BackgroundScheduler executorScheduler: Scheduler,
                                       @MainScheduler postExecutionScheduler: Scheduler,
                                       private val moviesRepositoryInterface: MoviesRepositoryInterface)
    : SingleUseCase<Page<Movie>, SearchMovies.Request>(executorScheduler, postExecutionScheduler) {

    override fun getRawSingle(param: Request): Single<Page<Movie>> {
        return moviesRepositoryInterface.searchMovies(param.searchQuery, param.page)
                .zipWith(moviesRepositoryInterface.getFavoriteMovies(),
                        BiFunction { page: Page<Movie>, favoriteIds: Set<String> ->
                            Page(page.items.map { it.copy(isFavorite = favoriteIds.contains(it.imdbId)) }, page.totalResults)
                        })
    }

    class Request(val searchQuery: String, val page: Long = 1)
}