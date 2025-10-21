package com.example.vijayiwfhassignment.domain.models

import kotlinx.serialization.SerialName

data class Title(
    val id: Int,
    val title: String,
    val year: Int, val imdbId: String?,
    val tmdbId: Int?,
    val tmdbType: String?
)