package com.example.vijayiwfhassignment.presentation.screens.detail_screen

import androidx.lifecycle.ViewModel
import com.example.vijayiwfhassignment.domain.repo.TitlesRepository
import com.example.vijayiwfhassignment.presentation.screens.home_screen.HomeUiState
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class DetailsViewModel(
    private val titleId: Int,
    private val repository: TitlesRepository
) : ViewModel() {



    private val _state = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val state = _state.asStateFlow()

    private val compositeDisposable = CompositeDisposable()

    init {
        fetchTitleDetails()
    }

    private fun fetchTitleDetails() {
        _state.value = DetailsUiState.Loading
        val disposable = repository.getTitleDetails(titleId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { details ->
                    _state.value = DetailsUiState.Success(details)
                },
                { throwable ->
                    // Map throwable to proper message
                    val message = when (throwable) {
                        is UnknownHostException -> "No internet connection or unable to reach server."
                        is SocketTimeoutException -> "Request timed out. Please try again."
                        is HttpException -> mapHttpError(throwable.code())
                        else -> throwable.message ?: "An unknown error occurred"
                    }
                    _state.value = DetailsUiState.Error(message)
                }
            )
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun mapHttpError(code: Int): String = when (code) {
        200 -> "Request successful."
        400 -> "Bad Request: Your request parameters are incorrect."
        401 -> "Unauthorized: Invalid or missing API key."
        403 -> "Forbidden: Your API key doesn't have permission for this request."
        404 -> "Not Found: The requested resource doesn't exist."
        429 -> "Too Many Requests: You have hit the rate limit. Please try again later."
        500 -> "Internal Server Error: Something went wrong on Watchmode's server."
        502 -> "Bad Gateway: Watchmode server received an invalid response."
        503 -> "Service Unavailable: Watchmode server is temporarily down or busy."
        504 -> "Gateway Timeout: Watchmode server took too long to respond."
        else -> "Unexpected Error: HTTP $code"
    }
}