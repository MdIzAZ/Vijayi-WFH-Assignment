package com.example.vijayiwfhassignment.presentation.screens.detail_screen

import com.example.vijayiwfhassignment.data.models.TitleDetailsResponse
import com.example.vijayiwfhassignment.domain.models.TitleDetails

sealed class DetailsUiState {
    object Loading : DetailsUiState()
    data class Success(val details: TitleDetails) : DetailsUiState()
    data class Error(val message: String) : DetailsUiState()
}