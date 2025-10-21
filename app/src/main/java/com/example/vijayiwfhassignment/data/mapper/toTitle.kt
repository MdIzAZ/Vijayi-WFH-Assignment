package com.example.vijayiwfhassignment.data.mapper

import com.example.vijayiwfhassignment.data.models.TitleDto
import com.example.vijayiwfhassignment.domain.models.Title

fun TitleDto.toTitle(): Title {
    return Title(
        id = this.id,
        title = this.title,
        year = this.year,
        imdbId = this.imdbId,
        tmdbId = this.tmdbId,
        tmdbType = this.tmdbType,
    )
}



