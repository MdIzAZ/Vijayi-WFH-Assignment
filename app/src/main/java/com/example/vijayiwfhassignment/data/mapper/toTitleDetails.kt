package com.example.vijayiwfhassignment.data.mapper

import com.example.vijayiwfhassignment.data.models.TitleDetailsResponse
import com.example.vijayiwfhassignment.domain.models.TitleDetails

fun TitleDetailsResponse.toTitleDetails(): TitleDetails {
    return TitleDetails(
        id = this.id,
        title = this.title,
        plotOverview = this.plotOverview,
        releaseDate = this.releaseDate,
        poster = this.poster,
        year = this.year
    )
}


