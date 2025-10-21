package com.example.vijayiwfhassignment.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TitleDetailsResponse(
    val id: Int,
    val title: String,
    @SerialName("plot_overview")
    val plotOverview: String?,
    @SerialName("release_date")
    val releaseDate: String?,
    val poster: String?,
    val year: Int
)