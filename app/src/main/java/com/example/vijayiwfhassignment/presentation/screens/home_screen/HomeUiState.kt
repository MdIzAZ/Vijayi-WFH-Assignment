package com.example.vijayiwfhassignment.presentation.screens.home_screen

import com.example.vijayiwfhassignment.domain.models.TitleDetails

sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val movies: List<TitleDetails>, val tvShows: List<TitleDetails>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}