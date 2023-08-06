package com.oguzhancetin.catapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.oguzhancetin.core_data.repository.CatRepository
import com.oguzhancetin.core_model.model.CatImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.mapLatest

import kotlinx.coroutines.flow.stateIn

import javax.inject.Inject

sealed class MainUIState(
    val catsList: List<CatImage>? = null,
    val errorMessage: String? = null,
) {
    data object Loading : MainUIState()
    class Success(data: List<CatImage>, errorMessage: String?) : MainUIState(catsList = data)
    class Error(errorMessage: String?) : MainUIState(errorMessage = errorMessage)
}

@HiltViewModel
class MainViewModel @Inject constructor(
    val catRepository: CatRepository,
) : ViewModel() {
    private val TAG = "MainViewModel"

    private val loading = MutableStateFlow(false)
    private val errorMessage = MutableStateFlow<String?>(null)

    private val _cats = catRepository.fetchCatImages(
        onStart = {
            loading.value = true
        },
        onComplete = {
            loading.value = false
        },
        onError = {
            loading.value = false
            errorMessage.value = it
        }
    )

    val mainUIState = combine(
        loading,
        errorMessage,
        _cats
    ) { loading, errorMessage, cats ->
        when {
            loading -> MainUIState.Loading
            cats.isEmpty().not() && errorMessage != null -> MainUIState.Success(
                cats,
                errorMessage = errorMessage
            )//may be fetch local but exception from network
            errorMessage != null -> MainUIState.Error(errorMessage)
            else -> MainUIState.Success(cats, errorMessage = null)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), MainUIState.Loading)

}