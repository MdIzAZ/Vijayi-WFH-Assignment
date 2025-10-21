package com.example.vijayiwfhassignment.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleDto(
    val id: Int,
    val title: String,
    val year: Int,
    @SerialName("imdb_id") val imdbId: String?,
    @SerialName("tmdb_id") val tmdbId: Int?,
    @SerialName("tmdb_type") val tmdbType: String?
)