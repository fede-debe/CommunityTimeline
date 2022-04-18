package com.example.communitymessages.presentation.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.map
import com.example.communitymessages.domain.model.asUiModel
import com.example.communitymessages.domain.model.local.Message
import com.example.communitymessages.domain.repository.ContentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    contentRepository: ContentRepository
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean?>()
    val isLoading: LiveData<Boolean?> = _isLoading

    private val _networkError = MutableLiveData<Boolean?>()
    val networkError: LiveData<Boolean?> = _networkError

    private val _navigateToMessageDetails = MutableLiveData<Message?>()
    val navigateToMessageDetails: LiveData<Message?> = _navigateToMessageDetails

    init {
        viewModelScope.launch {
            contentRepository.refreshTimeline()
        }
    }

    val getTimeline = contentRepository.getTimeline().map { pagingData ->
        pagingData.map { it.asUiModel() }
    }

    fun openMessageDetails(message: Message) {
        _navigateToMessageDetails.postValue(message)
    }

    fun openMessageDetailsComplete() {
        _navigateToMessageDetails.postValue(null)
    }
}