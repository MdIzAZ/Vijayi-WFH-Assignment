package com.example.vijayiwfhassignment.data.models

import kotlinx.serialization.Serializable


@Serializable
data class TitlesResponse(
    val titles: List<TitleDto>
)



