package com.example.vijayiwfhassignment.domain.repo

import com.example.vijayiwfhassignment.data.models.TitleDto
import com.example.vijayiwfhassignment.domain.models.Title
import com.example.vijayiwfhassignment.domain.models.TitleDetails
import io.reactivex.rxjava3.core.Single



interface TitlesRepository {

    fun getMoviesAndTvShows(): Single<Pair<List<TitleDetails>, List<TitleDetails>>>

    fun getTitleDetails(titleId: Int): Single<TitleDetails>
}