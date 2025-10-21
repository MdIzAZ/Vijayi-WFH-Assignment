package com.example.vijayiwfhassignment.domain.models


data class TitleDetails(
    val id: Int,
    val title: String,
    val plotOverview: String?,
    val releaseDate: String?,
    val poster: String?,
    val year: Int
)